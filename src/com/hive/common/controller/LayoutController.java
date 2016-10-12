package com.hive.common.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dk.controller.BaseController;

@Controller
@RequestMapping({"/layout"})
public class LayoutController extends BaseController
{
  @RequestMapping({"/index"})
  public String index()
  {
    return "index";
  }

  @RequestMapping({"/north"})
  public String north()
  {
    return "layout/north";
  }

  @RequestMapping({"/west"})
  public String west()
  {
    return "layout/west";
  }

  @RequestMapping({"/center"})
  public String center()
  {
    return "layout/center";
  }

  @RequestMapping({"/south"})
  public String south()
  {
    return "layout/south";
  }
}
