package rut.miit.beautySalon.controllers;

import com.example.beautySalon_contracts.controllers.BeautySalonController;
import com.example.beautySalon_contracts.form.edit.BeautySalonEditFormModel;
import com.example.beautySalon_contracts.form.edit.ClientEditFormModel;
import com.example.beautySalon_contracts.viewModel.BaseViewModel;
import com.example.beautySalon_contracts.viewModel.BeautySalonViewModel;
import com.example.beautySalon_contracts.viewModel.edit.BeautySalonEditViewModel;
import com.example.beautySalon_contracts.viewModel.edit.ClientEditViewModel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rut.miit.beautySalon.services.BeautySalonService;
import rut.miit.beautySalon.services.dtos.BeautySalonDto;
import rut.miit.beautySalon.services.dtos.ClientDto;

import java.security.Principal;

@Controller
@RequestMapping("")
public class BeautySalonControllerImpl implements BeautySalonController {
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    private final BeautySalonService beautySalonService;

    public BeautySalonControllerImpl (BeautySalonService beautySalonService) {
        this.beautySalonService = beautySalonService;
    }

    @Override
    @GetMapping("")
    public String BeautySalonView(Principal principal, Model model) {
        if (principal != null)
            LOG.log(Level.INFO, "Show info of beautySalon for " + principal.getName());
        BeautySalonDto beautySalonDto = beautySalonService.findAll().get(0);
        var viewModel = new BeautySalonViewModel(
                createBaseViewModel("Салон красоты"),
                beautySalonDto.getId(),
                beautySalonDto.getName(),
                beautySalonDto.getEmail(),
                beautySalonDto.getAddress(),
                beautySalonDto.getType()
        );
        model.addAttribute("model", viewModel);
        return "beautySalon-profile";
    }

    @Override
    @GetMapping("/beautySalon/{id}/edit")
    public String editForm(Principal principal, String id, Model model) {
        LOG.log(Level.INFO, "Show edit form of beautySalon for " + principal.getName());
        BeautySalonDto beautySalonDto = beautySalonService.findById(id);
        var viewModel = new BeautySalonEditViewModel(createBaseViewModel("Редактирование салона"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new BeautySalonEditFormModel(beautySalonDto.getId(), beautySalonDto.getName(), beautySalonDto.getAddress(), beautySalonDto.getEmail(), beautySalonDto.getType()));
        return "beautySalon-edit";
    }

    @Override
    @PostMapping("/beautySalon/{id}/edit")
    public String edit(String id, BeautySalonEditFormModel form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            var viewModel = new BeautySalonEditViewModel(createBaseViewModel("Редактирование салона"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", form);
            return "beautySalon-edit";
        }
        BeautySalonDto beautySalonDto = new BeautySalonDto(form.id(), form.name(), form.type(), form.address(), form.email());

        beautySalonService.update(beautySalonDto);
        return "redirect:/";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}
