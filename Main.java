import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class Main extends Application {

    private final String[] groups = {
            "Technology", "Banking", "Aviation",
            "Shopping", "Oil", "Food"
    };
    private final String[][] companyData = {
            CompanyData.techCompanies, CompanyData.bankCompanies, CompanyData.aerospaceCompanies,
            CompanyData.departmentStoreCompanies, CompanyData.oilCompanies, CompanyData.foodCompanies
    };
    private final double[][][] stockData = {
            CompanyData.techStockData, CompanyData.bankStockData, CompanyData.aerospaceStockData,
            CompanyData.departmentStoreStockData, CompanyData.oilStockData, CompanyData.foodStockData
    };

    @Override
    public void start(Stage primaryStage) {
        // Create the label for the choice box
        Text industryLabel = new Text("Select an Industry:");

        // Create the choice box for company group selection
        ChoiceBox<String> groupChoiceBox = new ChoiceBox<>();
        groupChoiceBox.getItems().addAll(groups);
        groupChoiceBox.setValue(groups[0]);

        // Create the line chart to display the stock data
        NumberAxis xAxis = new NumberAxis(1998, 2022, 1);
        NumberAxis yAxis = new NumberAxis();
        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Historical Stock Performance");
        lineChart.setCreateSymbols(false);

        // Custom formatter for the x-axis to display years as integers
        xAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                return String.valueOf(object.intValue());
            }

            @Override
            public Number fromString(String string) {
                return Integer.parseInt(string);
            }
        });

        // Create a Text node for displaying the description text
        Text descriptionText = new Text();
        descriptionText.setWrappingWidth(800); // Adjust the width as needed

        // Add the choice box listener to update the line chart and description text when a group is selected
        groupChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int selectedGroupIndex = groupChoiceBox.getSelectionModel().getSelectedIndex();
            updateLineChart(lineChart, companyData[selectedGroupIndex], stockData[selectedGroupIndex]);

            // Update the description text based on the selected group
            String description = getDescriptionForGroup(selectedGroupIndex);
            descriptionText.setText(description);
        });

        // Create a Button for showing the sources
        Button sourcesButton = new Button("Sources");
        sourcesButton.setOnAction(e -> showSourcesPopup());

        // Use an HBox to position the button to the right of the choice box
        HBox headerBox = new HBox(10, industryLabel, groupChoiceBox, sourcesButton);

        // Combine the header layout and the chart+description layout into the main layout
        VBox root = new VBox(headerBox, lineChart, descriptionText);
        root.setPadding(new javafx.geometry.Insets(10));
        root.setSpacing(10);

        // Set up the scene and show the stage
        Scene scene = new Scene(root, 820, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Un-Essay Project");
        primaryStage.show();
    }

    // Helper method to retrieve the description for each group (you can modify the descriptions as needed)
    private String getDescriptionForGroup(int groupIndex) {
        String[] descriptions = {
                "Tech",
                "Bank ",
                "Aviation",
                "Department",
                "Oil",
                "Food"
        };
        return descriptions[groupIndex];
    }

    private void updateLineChart(LineChart<Number, Number> lineChart, String[] companyNames, double[][] stockData) {
        // Clear existing data from the chart
        lineChart.getData().clear();

        // Add data points for each company to the chart
        for (int i = 0; i < companyNames.length; i++) {
            XYChart.Series<Number, Number> series = new XYChart.Series<>();
            series.setName(companyNames[i]);

            // Add data points to the series based on the provided stock data
            for (int j = 0; j < stockData[i].length; j++) {
                // Add data points to the series with the correct x-axis values (years)
                series.getData().add(new XYChart.Data<>(1998 + j, stockData[i][j]));
            }

            // Add the series to the line chart
            lineChart.getData().add(series);
        }
    }

    // Method to show the sources popup
    private void showSourcesPopup() {
        // You can customize the content and appearance of the popup as needed
        String sources = "Sources:\n"
                + "Tech: ...\n"
                + "Bank: ...\n"
                + "Aviation: ...\n"
                + "Department: ...\n"
                + "Oil: ...\n"
                + "Food: ...";

        Stage popupStage = new Stage();
        popupStage.setTitle("Sources");
        VBox popupLayout = new VBox(new Text(sources));
        Scene popupScene = new Scene(popupLayout, 400, 300);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
