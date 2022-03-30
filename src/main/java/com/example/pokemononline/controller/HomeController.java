package com.example.pokemononline.controller;


import com.example.pokemononline.model.Pokemon;
import com.example.pokemononline.repository.PokemonRepositoy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    PokemonRepositoy pokemonRepositoy;

    public HomeController(PokemonRepositoy pokemonRepositoy) {
        this.pokemonRepositoy = pokemonRepositoy;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pokemons", pokemonRepositoy.selectAll());
        return "index";
    }

    @GetMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("Name", "Frederik");
        model.addAttribute("Class", "Dat21B");
        return "thymeleaf";
    }

    @GetMapping("/formtest")
    public String formtestvis(Model model){
        return "formtest";
    }

    @PostMapping("/formtest")
    public String formtest(@RequestParam("name") String navn, @RequestParam("pwd") String kodeord, Model model) {
        System.out.println(navn + ", " + kodeord);
        model.addAttribute("mitNavn", navn);
        model.addAttribute("mitHemmeligeKodeord", kodeord);
        return "redirect:/formtest"; // redirect:/URL_NAVN laver en get reqeust på den givet URL
    }

    @PostMapping("/create")
    public String createPokemon(@RequestParam("pokedex_number") int pokedexNumber, @RequestParam("name") String navn, @RequestParam("speed") int speed, @RequestParam("special_defence") int special_defence,
                                @RequestParam("special_attack") int special_attack, @RequestParam("defence") int defence, @RequestParam("attack") int attack,
                                @RequestParam("hp") int hp, @RequestParam("primary_type") String primary_type, @RequestParam("secondary_type") String secondary_type){
        //Lav ny pokemon
        Pokemon newPokemon = new Pokemon(pokedexNumber, navn, speed, special_defence, special_attack, defence, attack, hp, primary_type, secondary_type);

        //Gem ny pokemon
        pokemonRepositoy.createPokemon(newPokemon);

        //Vis hele listen
        return "redirect:/";
    }

    @GetMapping("/create")
    public String createPokemonVis(Model model){
        return "create";
    }

    @GetMapping("/update/{id}")
    public String updatePokemon(@PathVariable("id") int id, Model model){

        return "/update";
    }

    @PostMapping("/update")
    public String updatePokemon(@ModelAttribute Pokemon pokemon){






        return "redirect:/";
    }

    @GetMapping("/delete/{id}")
    public String deletePokemon(@PathVariable("id") int id){

        pokemonRepositoy.deleteByID(id);
        return "redirect:/";
    }

}
