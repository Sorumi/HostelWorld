package edu.nju.hostelworld.controller;

import edu.nju.hostelworld.model.Level;
import edu.nju.hostelworld.service.LevelService;
import edu.nju.hostelworld.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Sorumi on 17/3/2.
 */

@Controller
@RequestMapping("/admin")
@SessionAttributes({"manager"})
public class ManagerLevelController {

    @Autowired
    private LevelService levelService;

    @RequestMapping(value = "/level", method = RequestMethod.GET)
    public String level(ModelMap model) {

        if (model.get("manager") == null) {
            return "redirect:/admin/login";
        }

        List<Level> levelList = levelService.findAllLevels();
        model.addAttribute("levelList", levelList);

        model.addAttribute("title", "等级管理");
        model.addAttribute("nav", "nav-level");

        return "manager-level";
    }

    @RequestMapping(value = "/level/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public String levelDelete(@PathVariable("id") int id, ModelMap model) {

        if (model.get("manager") == null) {
            return "redirect:/admin/login";
        }

        ResultMessage resultMessage = levelService.deleteLevel(id);
        return resultMessage.toString();
    }

    @RequestMapping(value = "/level/{id}/add", method = RequestMethod.POST)
    @ResponseBody
    public String levelAdd(@RequestBody Level level ,@PathVariable("id") int id, ModelMap model) {
        level.setID(id);
        Level oldLevel = levelService.findLevelByID(id);
        ResultMessage resultMessage;
        if (oldLevel == null) {
            resultMessage = levelService.addLevel(level);
        } else {
            resultMessage = levelService.updateLevel(level);
        }
        return resultMessage.toString();
    }
}
