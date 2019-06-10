package org.launchcode.controllers;

import org.launchcode.models.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("search")
public class SearchController {

    @RequestMapping(value = "")
    public String search(Model model) {
        model.addAttribute("columns", ListController.columnChoices);
        return "search";
    }

    // TODO #1 - Create handler to process search request and display results

   @RequestMapping(value = "results")
   public static String search(Model model, @RequestParam String searchType, @RequestParam String searchTerm) {
        if(searchType.equals("all") && searchTerm.equals("")) {
            ArrayList<HashMap<String, String>> jobs = JobData.findAll();
            model.addAttribute("jobs", jobs);
        } else if(searchType.equals("all")) {
            ArrayList<String> cols = new ArrayList<String>();

            cols.add("core competency");
            cols.add("employer");
            cols.add("name");
            cols.add("location");
            cols.add("position type");

            ArrayList<HashMap<String, String>> jobs = new ArrayList<HashMap<String, String>>();

            for (String x : cols) {
                ArrayList<HashMap<String, String>> someJobs = JobData.findByColumnAndValue(x, searchTerm);
                for (HashMap<String, String> j : someJobs) {
                    if (!jobs.contains(j)) {
                        jobs.add(j);
                    }
                }
            }
           model.addAttribute("jobs", jobs);
        } else {
            ArrayList<HashMap<String, String>> jobs = JobData.findByColumnAndValue(searchType, searchTerm);
            model.addAttribute("jobs", jobs);
        }

       model.addAttribute("columns", ListController.columnChoices);
       return "search";
   }


}
