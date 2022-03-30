package com.example.pokemononline.repository;


import com.example.pokemononline.model.Pokemon;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PokemonRepositoy {

    private final static String DB_URL = "JDBC:MYSQL://localhost:3306/pokedex";
    private final static String UID = "pokedex_user"; //root <--- Master bruger
    private final static String PWD = "test123"; //DatStudKea
    private Connection connection;

    //Vis hele listen af pokedexet
    public List<Pokemon> selectAll() {
        connectToMySQL();

        List<Pokemon> pokemons = new ArrayList<>();

        try { //Dette try statement trækker informationerne fra databasen
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM pokedex.pokemon";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) { //Så længe der er noget at hente fra databasen tilføjer laver den objekter og ligger dem i en liste

                int pokedex_number = resultSet.getInt("pokedex_number");
                String name = resultSet.getString("name");
                int speed = resultSet.getInt("speed");
                int special_defence = resultSet.getInt("special_defence");
                int special_attack = resultSet.getInt("special_attack");
                int defence = resultSet.getInt("defence");
                int attack = resultSet.getInt("attack");
                int hp = resultSet.getInt("hp");
                String primary_type = resultSet.getString("primary_type");
                String secondary_type = resultSet.getString("secondary_type");

                pokemons.add(new Pokemon(pokedex_number, name, speed, special_defence, special_attack, defence, attack, hp, primary_type, secondary_type));

            }
            statement.close();
        } catch (Exception e) {
            System.out.println("Noget gik galt");
        }
        return pokemons;
    }

    //Gem ny pokemon
    public void createPokemon(Pokemon pokemon) {

        //connect
        connectToMySQL();

        try {
            //prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO pokedex.pokemon(pokedex_number, name, speed, special_defence, special_attack," +
                            "defence, attack, hp, primary_type, secondary_type) VALUES (?,?,?,?,?,?,?,?,?,?)");

            //set attributter
            preparedStatement.setInt(1, pokemon.getNumber());
            preparedStatement.setString(2, pokemon.getName());
            preparedStatement.setInt(3, pokemon.getSpeed());
            preparedStatement.setInt(4, pokemon.getSpecial_defence());
            preparedStatement.setInt(5, pokemon.getSpecial_attack());
            preparedStatement.setInt(6, pokemon.getDefence());
            preparedStatement.setInt(7, pokemon.getAttack());
            preparedStatement.setInt(8, pokemon.getHp());
            preparedStatement.setString(9, pokemon.getPrimary_type());
            preparedStatement.setString(10, pokemon.getSecondary_type());

            //execute statement
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Noget gik galt *shrugs*");
            e.printStackTrace();
        }


    }

    //Opdatere pokemon
    public void updatePokemon(Pokemon pokemon) {

        //SQL syntax
        final String UPDATE_QUERY =
                "UPDATE pokemon SET name = ?, speed = ?, special_defence = ?, special_attack = ?" +
                        ", defence = ?, attack = ?, hp = ?, primary_type = ?, secondary_type = ? WHERE pokedex_number = ?";

        int pokeNumber = pokemon.getNumber();
        String name = pokemon.getName();
        int speed = pokemon.getSpeed();
        int specialDefense = pokemon.getSpecial_defence();
        int specialAttack = pokemon.getSpecial_attack();
        int defence = pokemon.getDefence();
        int attack = pokemon.getAttack();
        int hp = pokemon.getHp();
        String primaryType = pokemon.getPrimary_type();
        String secondaryTyoe = pokemon.getSecondary_type();

        //connect
        connectToMySQL();

        try {
            //prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY); //<--- SQL syntax

            //set attributter
            preparedStatement.setInt(1, pokeNumber);
            preparedStatement.setString(2,name);
            preparedStatement.setInt(3, speed);
            preparedStatement.setInt(4, specialDefense);
            preparedStatement.setInt(5, specialAttack);
            preparedStatement.setInt(6, defence);
            preparedStatement.setInt(7, attack);
            preparedStatement.setInt(8, hp);
            preparedStatement.setString(9, primaryType);
            preparedStatement.setString(10, secondaryTyoe);

            //execute statement
            preparedStatement.executeUpdate();
            System.out.println("Pokemon updated");

        } catch (Exception e) {
            System.out.println("Noget gik galt");
        }


    }

    //Delete by ID
    public void deleteByID(int id) {
        //connect
        connectToMySQL();

        try {
            //Create prepare statement
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM pokemon WHERE pokedex_number = ?");

            //set parameter
            preparedStatement.setInt(1, id);
            //execute statement

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            System.out.println("Noget gik galt ved sletningen");
        }
    }

    void connectToMySQL() { //Opretter forbindelse til databasen
        if (connection != null)
            connection = connection;
        try {
            connection = DriverManager.getConnection(DB_URL, UID, PWD);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
