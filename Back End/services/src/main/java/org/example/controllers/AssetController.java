package org.example.controllers;

import org.example.exceptions.EntityNotActive;
import org.example.exceptions.EntityNotFound;
import org.example.services.AssetService;
import org.example.views.in.AssetInView;
import org.example.views.out.AssetOutView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/asset")
public class AssetController {
    
    @Autowired
    private AssetService assetService;
    
    @GetMapping("/")
    public ResponseEntity<Page<AssetOutView>> getAll(Pageable pageable){
        Page<AssetOutView> res = assetService.getAll(pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<AssetOutView>> getById(@PathVariable("id") Long id){

        Optional<AssetOutView> res = assetService.getById(id);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res);
    }

    @PostMapping("/add")
    public ResponseEntity<AssetOutView> addOne(@Validated @RequestBody AssetInView body){
        AssetOutView res = assetService.addOne(body);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@PathVariable("id") Long id) throws EntityNotFound, EntityNotActive {
        assetService.deleteOne(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetOutView> updateOne(@PathVariable("id") Long id,@Validated @RequestBody AssetInView body) throws EntityNotFound {
        AssetOutView res = assetService.updateOne(id,body);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/by_user/{userId}")
    public ResponseEntity<Page<AssetOutView>> getAllByUser(@PathVariable("userId")Long userId, Pageable pageable){
        Page<AssetOutView> res = assetService.getAllByUser(userId,pageable);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/by_name/{name}/{userId}")
    public ResponseEntity<Optional<AssetOutView>> getByName(@PathVariable("name") String name, @PathVariable("userId") Long userId){

        Optional<AssetOutView> res = assetService.getByName(userId, name);
        if(res.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(res);
    }
}
