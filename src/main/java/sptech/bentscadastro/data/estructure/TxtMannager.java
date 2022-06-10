package sptech.bentscadastro.data.estructure;

import net.bytebuddy.asm.Advice;
import org.springframework.web.multipart.MultipartFile;
import sptech.bentscadastro.menufood.entity.MenuFood;
import sptech.bentscadastro.menufood.form.UpdateItemMenuFoodForm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TxtMannager {

    public List<MenuFood> txtReaderMenuFood(String nomeArq) {

        BufferedReader entrada = null;

        String registro, tipoRegistro;
        String nameFood, description;
        Double price;

        List<MenuFood> listFood = new ArrayList<>();


        // Abre o arquivo para leitura

        try {
            entrada = new BufferedReader(new FileReader(nomeArq));
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
                    System.out.println("Registro Invalido!");
                }
                registro = entrada.readLine();
            }
            entrada.close();
        } catch (IOException erro) {
            System.out.println("Erro ao ler o tipo de registro: " + erro);
        }

        return listFood;

    }
}
