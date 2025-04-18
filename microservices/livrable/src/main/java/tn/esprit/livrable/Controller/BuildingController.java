package tn.esprit.livrable.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.livrable.entity.Building3DData;
import tn.esprit.livrable.entity.BuildingModel;

@RestController
@RequestMapping("/model")
public class BuildingController {


    @PostMapping
    public Building3DData generateBuilding(@RequestBody BuildingModel model) {
        return model.generate3DData();
    }




}
