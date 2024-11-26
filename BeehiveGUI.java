import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class BeehiveGUI extends Application {

    private Beehive hive;
    private boolean x = false; //condtion to check wether the bee was killed or not
    private int honeyproduced = 0;
    private File saveFile = new File("hive_data.txt");

    @Override
    public void start(Stage primaryStage) {
        hive = null;
    
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setStyle("-fx-background-color: yellow;");
        Label titleLabel = new Label("Create Your Own Beehive");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);

        Label queenNameLabel = new Label("Enter Queen Bee's Name:");
        GridPane.setConstraints(queenNameLabel, 0, 1);
        TextField queenNameField = new TextField();
        GridPane.setConstraints(queenNameField, 1, 1);

        Label eggLayingRateLabel = new Label("Enter Queen Bee's Egg Laying Rate:");
        GridPane.setConstraints(eggLayingRateLabel, 0, 2);
        TextField eggLayingRateField = new TextField();
        GridPane.setConstraints(eggLayingRateField, 1, 2);

        Button createHiveButton = new Button("Create Hive");
        GridPane.setConstraints(createHiveButton, 0, 3, 2, 1);

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        GridPane.setConstraints(outputArea, 0, 4, 2, 1);

        createHiveButton.setOnAction(e -> {
            String queenName = queenNameField.getText();
            String eggLayingRateText = eggLayingRateField.getText();
            try {
                int eggLayingRate = Integer.parseInt(eggLayingRateText);
                QueenB queen = new QueenB(queenName, 0, 0, true, eggLayingRate, 0);
                hive = new Beehive(queen);
                outputArea.appendText("Hive created with Queen Bee: " + queenName + "\n");
                
                // save data to file
                saveData();
            } catch (NumberFormatException ex) {
                outputArea.appendText("Error: Egg laying rate must be a valid integer.\n");
            } catch (Exception ex) {
                outputArea.appendText("Error: " + ex.getMessage() + "\n");
                ex.printStackTrace();
            }
        });
        
        
        Button addBeeButton = new Button("Add Bee");
        GridPane.setConstraints(addBeeButton, 0, 5);
        TextField beeNameField = new TextField();
        beeNameField.setPromptText("Enter Bee's Name");
        GridPane.setConstraints(beeNameField, 1, 5);

        addBeeButton.setOnAction(e -> {
            if (hive != null) {
                String beeName = beeNameField.getText().trim(); 
                
                
                if (!beeName.isEmpty()) {
                    // bee already exists
                    boolean beeExists = hive.getBees().stream().anyMatch(bee -> bee.getName().equalsIgnoreCase(beeName));
                    if (!beeExists) {
                        Bee bee = new Bee(beeName, 0, 0, true);
                        hive.addBee(bee);
                        outputArea.appendText("Bee added: " + beeName + "\n");
                    } else {
                        outputArea.appendText("Bee with the same name already exists!\n");
                    }
                } else {
                    outputArea.appendText("Please enter a valid bee name!\n");
                }
            } else {
                outputArea.appendText("Create a hive first!\n");
            }
        });
        

        Button attackButton = new Button("Attack a Random Bee!");
        GridPane.setConstraints(attackButton, 0, 6);

        attackButton.setOnAction(e -> {
            if (hive != null && !hive.getBees().isEmpty()) {
                Predator dog = new Predator("scoobydoo", 2);
                int randomBeeIndex = (int) (Math.random() * hive.getBees().size());
                Bee bee = hive.getBees().get(randomBeeIndex);
                outputArea.appendText("Oh no! Incoming danger! " + dog.getName() + " is attacking " + bee.getName() + "\n");
                
                int damage = dog.calculateDamage();
                bee.takeDamage(damage);
                
                outputArea.appendText("Damage done: " + bee.getTotalDamageTaken()+ "\n");
        
                if (!bee.getAliveStatus()) {
                    outputArea.appendText(bee.getName() + " has died and is removed from the hive.\n");
                    hive.removeBee(bee);
                }
            } else {
                outputArea.appendText("There are no bees in the hive to attack!\n");
            }
        });

        Button attackQueenButton = new Button("Attack Queen Bee");
        GridPane.setConstraints(attackQueenButton, 1, 6);

        attackQueenButton.setOnAction(e -> {
            Date timeofdeath;
        if (hive != null) {
            QueenB queen = hive.getQueen();
                if (queen != null && queen.getAliveStatus()) {
                 Predator dog = new Predator("scoobydoo", 2);
                  outputArea.appendText("Oh no! Incoming danger! " + dog.getName() + " is attacking Queen Bee " + queen.getName() + "\n");
                int damage = dog.calculateDamage();
                queen.takeDamage(damage);
                outputArea.appendText("Damage done: " + queen.getTotalDamageTaken() + "\n");
                 if (!queen.getAliveStatus()) {
                     outputArea.appendText(queen.getName() + " has died.\n");
                    hive.setQueen(null);
                    x = true;
                    timeofdeath = new Date();
                    outputArea.appendText("Time of death: " + timeofdeath +"\n");

                }
                } else {
                     outputArea.appendText("Queen Bee is already dead or doesn't exist!\n");
                    }
        } else {
        outputArea.appendText("Create a hive first!\n");
        } 
    });

    

    Button showNewsButton = new Button("Show Beehive News");
    GridPane.setConstraints(showNewsButton, 1, 7);
    
    showNewsButton.setOnAction(e -> {
        if (hive != null) {
            outputArea.clear();
            outputArea.appendText("Beehive News:\n");
    
            if (hive.getQueen() != null && hive.getQueen().getAliveStatus()) {
                outputArea.appendText("The Queen Bee " + hive.getQueen().getName() + " is alive and well!\n");
                outputArea.appendText("New eggs were laid and honey was made. The economy is blooming!\n");
                outputArea.appendText("Everyone is productive and Happy. Hooray!\n");
            } else if (hive.getQueen() == null && x) {
                outputArea.appendText("On Today's Unfortunate News:\n");
                outputArea.appendText("The Queen Bee has died. The bees are panicking!\n");
                outputArea.appendText("Quickly replace her!\n");
        
            } else {
                outputArea.appendText("Queen Bee has not been created yet!\n");
            }
    
            outputArea.appendText("Date: " + new Date() + "\n");
        } else {
            outputArea.appendText("Create a hive first!\n");
        }
    });

Button replaceQueenButton = new Button("Replace Queen Bee");
GridPane.setConstraints(replaceQueenButton, 0, 7);

replaceQueenButton.setOnAction(e -> {
    if (hive != null && hive.getQueen() == null && x) {

       TextInputDialog dialog = new TextInputDialog();
       dialog.setTitle("Replace Queen Bee");
      dialog.setHeaderText(null);
      dialog.setContentText("Enter a new name for the Queen Bee:");

        dialog.showAndWait().ifPresent(newQueenName -> {
            
           int eggLayingRate = Integer.parseInt(eggLayingRateField.getText());
           QueenB newQueen = new QueenB(newQueenName, 0, 0, true, eggLayingRate, 0);
           hive.setQueen(newQueen);
            outputArea.appendText("Queen Bee replaced with: " + newQueenName + "\n");
            
            // Reset 
          x = false;
       });
   } else {
        outputArea.appendText("Cannot replace Queen Bee. Either hive is null or Queen Bee exists or x is false.\n");
    }
});


Button produceHoneyButton = new Button("Produce Honey");
GridPane.setConstraints(produceHoneyButton, 0, 8);

produceHoneyButton.setOnAction(e -> {
    int totalHoneyProduced = 0; 
    if (hive != null) {
        for (Bee bee : hive.getBees()) {
            if (bee.getAliveStatus()) {
                int honeyProduced = bee.produceHoney();
                outputArea.appendText(bee.getName() + " produced " + honeyProduced + " units of honey.\n");
                totalHoneyProduced += honeyProduced; // Add honey
            } else {
                outputArea.appendText(bee.getName() + " is dead and cannot produce honey.\n");
            }
        }
        honeyproduced += totalHoneyProduced;
    } else {
        outputArea.appendText("Create a hive first!\n");
    }
});

Button retrieveHoneyButton = new Button("Retrieve Hive Honey");
GridPane.setConstraints(retrieveHoneyButton, 1, 8);

retrieveHoneyButton.setOnAction(e -> {
    if(hive!=null){
    outputArea.appendText("Total honey produced in the hive: " + honeyproduced + " units.\n");
    }else{
        outputArea.appendText("Create a hive first!\n");
    }
});

Button layEggsButton = new Button("Lay Eggs");
GridPane.setConstraints(layEggsButton, 0, 9);

layEggsButton.setOnAction(e -> {
    if (hive != null && hive.getQueen() != null) {
        QueenB queen = hive.getQueen();
        int timeUnitsRequired = queen.getEggLayingRate();
        int timeElapsed = queen.getTimeElapsed();
        
        if (timeElapsed >= timeUnitsRequired) {
            ArrayList<Bee> eggsLaid = queen.layEggs();
            outputArea.appendText("Queen laid " + eggsLaid.size() + " eggs.\n");
        } else {
            int remainingTime = timeUnitsRequired - timeElapsed;
            outputArea.appendText("Not enough time has passed for the Queen to lay eggs. Wait for " + remainingTime + " more time unit(s).\n");
        }
    }else if(x == true){
        outputArea.appendText("Queen has been killed. She can't lay eggs.\n");
    
    } else {
        outputArea.appendText("Queen Bee does not exist or hive is not created yet!\n");
    }
});

Button passTimeButton = new Button("Pass Time");
GridPane.setConstraints(passTimeButton, 1, 9);

passTimeButton.setOnAction(e -> {
    if (hive != null && hive.getQueen() != null) {
        hive.getQueen().passTime(10); 
        outputArea.appendText("10 time units have passed.\n");
    } else {
        outputArea.appendText("Queen Bee does not exist or hive is not created yet!\n");
    }
});
Plant plant = new Plant(10000);

Button collectNectarButton = new Button("Collect Nectar");
GridPane.setConstraints(collectNectarButton, 0, 10);

collectNectarButton.setOnAction(e -> {
    if (hive != null && !hive.getBees().isEmpty()) {
        for (Bee bee : hive.getBees()) {
            if (bee.getAliveStatus()) {
                bee.collectNectar(plant); 
                outputArea.appendText(bee.getName() + " is collecting nectar\n");
            } else {
                outputArea.appendText(bee.getName() + " is dead and cannot collect nectar.\n");
            }
        }
    } else {
        outputArea.appendText("There are no bees in the hive to collect nectar!\n");
    }
});

Button displayBeeInfoButton = new Button("Display Active Bee  Names");
GridPane.setConstraints(displayBeeInfoButton, 1, 10);

displayBeeInfoButton.setOnAction(e -> {
    if (hive != null && !hive.getBees().isEmpty()) {
        outputArea.appendText("Active bees:\n");
        for (Bee bee : hive.getBees()) {
            outputArea.appendText(bee.getName() + "\n");
        }
    } else {
        outputArea.appendText("There are no bees in the hive to display information!\n");
    }
});



        grid.getChildren().addAll(
                titleLabel,
                queenNameLabel, queenNameField,
                eggLayingRateLabel, eggLayingRateField,
                createHiveButton,
                outputArea,
                beeNameField, addBeeButton,
                attackButton, attackQueenButton,
                showNewsButton, replaceQueenButton,
                produceHoneyButton, retrieveHoneyButton,
                layEggsButton, passTimeButton,
                collectNectarButton, displayBeeInfoButton
                
        );

        Scene scene = new Scene(grid, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Beehive Management");
        primaryStage.show();
    }


    private void saveData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(saveFile))) {
            writer.write("Hive state: " + hive.toString() + "\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
