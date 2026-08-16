package com.dnd.watertasting;

import jakarta.validation.Valid;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;

@Controller
public class TastingController {

    private final TastingRepository repository;

    public TastingController(TastingRepository repository) {
        this.repository = repository;
    }

    @InitBinder
    void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    record MetricField(String id, String label, String caption, boolean highlight) {}

    record FormSection(String title, List<MetricField> fields) {}

    private static final List<FormSection> FORM_SECTIONS = List.of(
            new FormSection("Visuals - The Eye", List.of(
                    new MetricField("clarity", "Clarity", "Turbid / Cloudy [1] → Crystal Clear [5]", false),
                    new MetricField("color", "Color", "Colorless [1] → Tinted / Infused [5]", false))),
            new FormSection("Aroma - The Nose", List.of(
                    new MetricField("odor", "Odor", "None / Neutral [1] → Strong / Earthy / Metallic [5]", false))),
            new FormSection("Mouthfeel - The Texture", List.of(
                    new MetricField("carbonation", "Carbonation", "Still [1] → Highly Effervescent / Fizzy [5]", false),
                    new MetricField("temperature", "Temperature", "Ice Cold / Frigid [1] → Tepid / Room Temp [5]", false),
                    new MetricField("body", "Body / Weight", "Thin / Light [1] → Heavy / Silky [5]", false))),
            new FormSection("The Palate - Taste Profile", List.of(
                    new MetricField("mineralIntensity", "Mineral Intensity", "Low / None [1] → High / Intense [5]", false),
                    new MetricField("sweetness", "Sweetness", "Dry [1] → Sweet [5]", false),
                    new MetricField("bitterness", "Bitterness", "None [1] → Sharp / Bitter [5]", false),
                    new MetricField("salinity", "Salinity", "Fresh [1] → Salty / Brackish [5]", false),
                    new MetricField("phSensation", "pH Sensation", "Acidic / Sharp [1] → Alkaline / Smooth [5]", false))),
            new FormSection("The Finish - Aftertaste", List.of(
                    new MetricField("duration", "Duration", "Short / Clean [1] → Long / Lingering [5]", false))),
            new FormSection("Overall Assessment", List.of(
                    new MetricField("harmony", "Harmony", "Unbalanced / Harsh [1] → Perfectly Balanced [5]", false),
                    new MetricField("personalEnjoyment", "Personal Enjoyment", "Not my cup (or glass) [1] → Absolute Favorite [5]", true))));

    @GetMapping("/")
    public String index(Model model) {
        if (!model.containsAttribute("tasting")) {
            model.addAttribute("tasting", new WaterTasting());
        }
        model.addAttribute("sections", FORM_SECTIONS);
        return "index";
    }

    @PostMapping("/submit")
    public String submit(@Valid @ModelAttribute("tasting") WaterTasting tasting,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.tasting", bindingResult);
            redirectAttributes.addFlashAttribute("tasting", tasting);
            return "redirect:/";
        }
        repository.save(tasting);
        redirectAttributes.addFlashAttribute("tasting", new WaterTasting());
        redirectAttributes.addFlashAttribute("success",
                "Tasting recorded for sample \"" + tasting.getWaterSampleId() + "\". Well met, adventurer!");
        return "redirect:/";
    }

    @GetMapping("/results")
    public String results() {
        return "results";
    }

    @GetMapping("/api/tastings")
    @ResponseBody
    public List<TastingDto> tastingsJson() {
        return repository.findAllByOrderByCreatedAtDesc().stream()
                .map(TastingDto::from)
                .toList();
    }

    record NameCount(String name, long count) {}

    record MergeRequest(String target, List<String> sources) {}

    record MergeResponse(String target, int updated) {}

    @GetMapping("/api/samples")
    @ResponseBody
    public List<NameCount> samplesJson() {
        return toNameCounts(repository.countByWaterSampleId());
    }

    @PostMapping("/api/samples/merge")
    @ResponseBody
    public MergeResponse mergeSamples(@RequestBody MergeRequest request) {
        return merge(request, repository::mergeWaterSampleIds);
    }

    @GetMapping("/api/adventurers")
    @ResponseBody
    public List<NameCount> adventurersJson() {
        return toNameCounts(repository.countByAdventurerName());
    }

    @PostMapping("/api/adventurers/merge")
    @ResponseBody
    public MergeResponse mergeAdventurers(@RequestBody MergeRequest request) {
        return merge(request, repository::mergeAdventurerNames);
    }

    private List<NameCount> toNameCounts(List<Object[]> rows) {
        return rows.stream()
                .map(row -> new NameCount((String) row[0], (Long) row[1]))
                .toList();
    }

    private MergeResponse merge(MergeRequest request,
                                BiFunction<String, Collection<String>, Integer> mergeFn) {
        if (request == null || request.target() == null || request.target().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Target name must not be blank");
        }
        if (request.sources() == null || request.sources().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sources must not be empty");
        }
        String target = request.target().trim();
        // Sources must match DB rows verbatim (whitespace variants are the point), so never trim them.
        List<String> sources = request.sources().stream()
                .filter(s -> s != null && !s.equals(target))
                .toList();
        if (sources.isEmpty()) {
            return new MergeResponse(target, 0);
        }
        return new MergeResponse(target, mergeFn.apply(target, sources));
    }
}
