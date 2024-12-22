package rut.miit.beautySalon.controllers;

import com.example.beautySalon_contracts.controllers.FeedbackController;
import com.example.beautySalon_contracts.viewModel.BaseViewModel;
import com.example.beautySalon_contracts.viewModel.FeedbackListViewModel;
import com.example.beautySalon_contracts.viewModel.FeedbackViewModel;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rut.miit.beautySalon.services.FeedbackService;
import rut.miit.beautySalon.services.dtos.FeedbackDto;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/feedbacks")
public class FeedbackControllerImpl implements FeedbackController {

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private final FeedbackService feedbackService;

    public FeedbackControllerImpl(FeedbackService feedbackService){
        this.feedbackService = feedbackService;
    }

    @Override
    @GetMapping("")
    public String ListFeedbacks (Principal principal, Model model) {
        if (principal != null)
            LOG.log(Level.INFO, "Show list of feedbacks for " + principal.getName());

        List<FeedbackDto> feedbacks = feedbackService.findAll();
        System.out.println("SIZE: " + feedbacks.size());
        var feedbackViewModels = feedbacks.stream()
                .map(f -> new FeedbackViewModel(f.getId(), f.getMasterName(), f.getClientName(), f.getFeedback()))
                .toList();
        var viewModel = new FeedbackListViewModel(
                createBaseViewModel("Все отзывы"), feedbackViewModels);
        model.addAttribute("model", viewModel);
        return "client/feedback-list";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(
                title
        );
    }
}