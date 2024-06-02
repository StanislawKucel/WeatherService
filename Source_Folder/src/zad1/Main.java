/**
 *
 *  @author Kucel Stanislaw S24910
 *
 */

package zad1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;


public class Main extends Application {
  public static void main(String[] args) {
    Service s = new Service("Germany");
    String weatherJson = s.getWeather("Warsaw");
    System.out.println(weatherJson);
    Double rate1 = s.getRateFor("USD");
    System.out.println(rate1);
    Double rate2 = s.getNBPRate();
    System.out.println(rate2);
 
    
    launch(args);
  }
  
  @Override
  public void start(Stage primaryStage) {
      VBox root = new VBox(10);
      root.setPadding(new Insets(10, 10, 10, 10));

      HBox inputFields = new HBox(10);
      TextField countryField = new TextField();
      countryField.setPromptText("Country");
      TextField cityField = new TextField();
      cityField.setPromptText("City");
      TextField currencyCodeField = new TextField();
      currencyCodeField.setPromptText("Currency Code");

      inputFields.getChildren().addAll(countryField, cityField, currencyCodeField);

      Button submitButton = new Button("Get Info");

      Label weatherLabel = new Label();
      Label rateForLabel = new Label();
      Label nbpRateLabel = new Label();

      WebView webView = new WebView();
      WebEngine webEngine = webView.getEngine();
      webView.setPrefSize(800, 500);

      submitButton.setOnAction(e -> {
          Service service = new Service(countryField.getText());
          String weatherJson = service.getWeather(cityField.getText());
          Double rateFor = service.getRateFor(currencyCodeField.getText());
          Double nbpRate = service.getNBPRate();

         
          weatherLabel.setText("Weather: " + weatherJson);
          rateForLabel.setText("Rate for " + currencyCodeField.getText() + ": " + rateFor);
          nbpRateLabel.setText("NBP Rate: " + nbpRate);

          String cityName = cityField.getText().replace(" ", "_");
          String wikiUrl = "https://en.wikipedia.org/wiki/" + cityName;
          webEngine.load(wikiUrl);
      });

      root.getChildren().addAll(inputFields, submitButton, weatherLabel, rateForLabel, nbpRateLabel, webView);

      Scene scene = new Scene(root, 800, 600);
      primaryStage.setTitle("City Information");
      primaryStage.setScene(scene);
      primaryStage.show();
  }
}
