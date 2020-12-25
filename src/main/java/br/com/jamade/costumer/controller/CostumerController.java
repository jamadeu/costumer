package br.com.jamade.costumer.controller;

import br.com.jamade.costumer.service.CostumerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("costumers")
@RequiredArgsConstructor
public class CostumerController {
    private final CostumerService costumerService;


}
