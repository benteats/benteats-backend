package sptech.bentscadastro.data.estructure;

import net.bytebuddy.asm.Advice;
import org.springframework.web.multipart.MultipartFile;
import sptech.bentscadastro.menufood.entity.MenuFood;
import sptech.bentscadastro.menufood.form.UpdateItemMenuFoodForm;
import sptech.bentscadastro.restaurant.entity.Restaurant;
import sptech.bentscadastro.user.entity.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TxtMannager {

    public List<MenuFood> txtReaderMenuFood(String fileName) {

        BufferedReader entrada = null;

        String registro, tipoRegistro;
        String nameFood, description;
        Double price;

        List<MenuFood> listFood = new ArrayList<>();


        // Abre o arquivo para leitura

        try {
            entrada = new BufferedReader(new FileReader(fileName));
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo: " + erro);
        }

        // Leitura arquivo

        try {

            registro = entrada.readLine();

            while (registro != null) {

                tipoRegistro = registro.substring(0, 2);

                if (tipoRegistro.equals("02")) {

                    // Trim elimina os brancos à direita da String

                    nameFood = registro.substring(2, 47).trim();
                    price = Double.valueOf(registro.substring(47, 55).replace(",", "."));
                    description = registro.substring(55, 255).trim();

                    System.out.println("E um registro de Corpo Restaurante!");
                    System.out.println("Nome do Prato: " + nameFood);
                    System.out.println("Preço: " + price);
                    System.out.println("Descrição: " + description);

                    MenuFood menuItem = new MenuFood();

                    menuItem.setName(nameFood);
                    menuItem.setPrice(price);
                    menuItem.setDescription(description);

                    listFood.add(menuItem);


                } else {
                    System.out.println("Registro Diferente de Cardapio!");
                }
                registro = entrada.readLine();
            }
            entrada.close();
        } catch (IOException erro) {
            System.out.println("Erro ao ler o tipo de registro: " + erro);
        }

        return listFood;

    }

    public Queue txtReaderNewUser(String filename) {

        BufferedReader entrada = null;

        Stack stackUser = new Stack(20);
        Stack stackRestaurant = new Stack(20);

        Queue queueList = new Queue<>(40);

        String registro, tipoRegistro;

        // User
        String nameUser, password, typeUser, emailUser, cep, phoneUser, state, city, district, adress, adressNumber;
        Float latitude, longitude;

        // Restaurant
        String foodType, openingTime, closingTime, descRestaurant, priceAverage;


        // Abre o arquivo para leitura

        try {
            entrada = new BufferedReader(new FileReader(filename));
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo: " + erro);
        }

        // Leitura arquivo

        try {

            registro = entrada.readLine(); // Lê o primeiro registro do arquivo

            while (registro != null) { // Enquanto não for o final do arquivo

                tipoRegistro = registro.substring(0, 2); // Obtem os 2 primeiros caracteres do registro

               if (tipoRegistro.equals("01")) {

                    // Trim elimina os brancos à direita da String

                    password = registro.substring(2,37).trim();
                    typeUser = registro.substring(37,47).trim();
                    nameUser = registro.substring(47, 82).trim();
                    emailUser = registro.substring(82,142).trim();
                    phoneUser = registro.substring(142,157).trim();
                    cep = registro.substring(157,165).trim();
                    state = registro.substring(165,195).trim();
                    city = registro.substring(195,245).trim();
                    district = registro.substring(245,295).trim();
                    adress = registro.substring(295,375).trim();
                    adressNumber = registro.substring(375,380).trim();
                    latitude = Float.valueOf(registro.substring(380,388).replace(",", "."));
                    longitude = Float.valueOf(registro.substring(388,396).replace(",", "."));


                    User newUser = new User();


                    newUser.setName(nameUser);
                    newUser.setEmail(emailUser);
                    newUser.setPassword(password);
                    newUser.setUserType(typeUser);
                    newUser.setPhone(phoneUser);
                    newUser.setCep(cep);
                    newUser.setState(state);
                    newUser.setCity(city);
                    newUser.setDistrict(district);
                    newUser.setAddress(adress);
                    newUser.setAddressNumber(adressNumber);
                    newUser.setLat(latitude);
                    newUser.setLng(longitude);


                    stackUser.push(newUser);


                } else if(tipoRegistro.equals("02")) {

                   foodType = registro.substring(2, 32).trim();
                   priceAverage = registro.substring(32, 39).trim();
                   openingTime = registro.substring(39, 44).trim();
                   closingTime = registro.substring(44, 49).trim();
                   descRestaurant = registro.substring(49,249).trim();


                   Restaurant newRestaurant = new Restaurant();


                   newRestaurant.setFoodType(foodType);
                   newRestaurant.setPriceAverage(priceAverage);
                   newRestaurant.setOpeningTime(openingTime);
                   newRestaurant.setClosingTime(closingTime);
                   newRestaurant.setDescription(descRestaurant);


                   stackRestaurant.push(newRestaurant);

                }
                else {
                    System.out.println("Registro Diferente de Usuário!");
                }
                // Ler o próximo registro!
                registro = entrada.readLine();
            }
            entrada.close();
        } catch (IOException erro) {
            System.out.println("Erro ao ler o tipo de registro: " + erro);
        }

        while (!stackUser.isEmpty() || !stackRestaurant.isEmpty()) {

            queueList.insert(stackUser.pop());
            queueList.insert(stackRestaurant.pop());

        }

        return queueList;
    }

}
