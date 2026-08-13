package com.dnd.watertasting;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class TastingController {

    private final TastingRepository repository;

    public TastingController(TastingRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String index(Model model) {
        if (!model.containsAttribute("tasting")) {
            model.addAttribute("tasting", new WaterTasting());
        }
        return "index";
    }

    @PostMapping("/submit")
    public String submit(@Valid @ModelAttribute("tasting") WaterTasting tasting,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "index";
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
    public List<WaterTasting> tastingsJson() {
        return repository.findAllByOrderByCreatedAtDesc();
    }
}
