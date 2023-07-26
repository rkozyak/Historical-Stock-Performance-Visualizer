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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Main extends Application {

    private final String[] groups = {
            "Technology", "Banking", "Aviation",
            "Retail", "Oil", "Food"
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
        Button sourcesButton = new Button("Sources/Citations");
        sourcesButton.setOnAction(e -> showSourcesPopup());

        // Use an HBox to position the button to the right of the choice box
        HBox headerBox = new HBox(10, industryLabel, groupChoiceBox, sourcesButton);

        // Combine the header layout and the chart+description layout into the main layout
        VBox root = new VBox(headerBox, lineChart, descriptionText);
        root.setPadding(new javafx.geometry.Insets(10));
        root.setSpacing(10);

        // Set up the scene and show the stage
        Scene scene = new Scene(root, 820, 750);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Un-Essay Project");
        primaryStage.show();
    }

    // Helper method to retrieve the description for each group (you can modify the descriptions as needed)
    private String getDescriptionForGroup(int groupIndex) {
        String[] descriptions = {
                "The technology industry is one of the most dynamic and rapidly evolving and growing sectors of the U.S. Economy and shapes and changes the world with its innovations and inventions. To represent this industry, I choose to graph the past stock prices for Amazon, Apple, and Google as they are some of the largest players and global leaders in the technology industry. \n" + //
                        "\n" + //
                        "The late 1990s marked the [dot.com](http://dot.com) boom and many technology companies saw an increase in unprecedented growth while some companies went under when that bubble burst, these three companies did very well at that time. With slow and steady growth for the next couple of years, major world events didn't really play a major part in increasing or decreasing their values. Instead, we start to see their values growing at an increasing rate in 2015 and eventually skyrocketing in 2019 and 2020 as we see Covid-19 having a major influence on these companies with people starting to work from home more [11].",
                "The banking industry is very tightly connected with the U.S Economy and since 1998, many major events that have affected the U.S. Economy and the US as a whole have severely affected the banking industry. To represent this industry, I chose to show data for Citi Group, Goldman Sachs, and Morgan Stanley as they are very prominent financial institutions and are major players in the financial section. However, it is important to remember they are not the only companies as there are other major players such as JPMorgan Chase, Bank of America, Wells Fargo, and other banks also contribute a significant part to the banking and financial industry. \n" + //
                        "\n" + //
                        "Starting in 1998 until 2001, the industry was doing fairly well but the first time we see a decline in this industry is during 2001 and the couple years following it. While there are many different events that can be contributed to this decline, I would consider the 9/11 Terrorist Attacks, Dot Com Bubble Burst, and the Early 2000s recession to have had significant impacts causing the downward trend from 2001 to 2003. The attacks after 9/11 also caused the VIX volatility index which measures market volatility to spike to the highest it has ever been recorded causing a massing strain on the financial industry [1]. \n" + //
                        "\n" + //
                        "The next major event that hurt the banking industry was the Financial Crisis in 2008 which can be cited due to these companies giving out subprime mortgages and the government being forced to bail out several banks including Citi Group [2]. All three of these companies had a major hit in their performance with the largest decline from Citi Group. The next major decline of these 3 companies happened from 2011 to 2012 and it can partially be blamed on the Libor scandal in which bankers manipulated interest rates for profit and the European Debt Crisis that also affected the global banking industry due to uncertainty during that period. The last significant decline that affected all three of these companies happened from 2018 to 2019 and can be partially attributed to the trade tensions between the United States and China and an overall economic slowdown globally. ",
                "The aviation industry is a critical part of the US Economy and plays a vital role in connecting people, goods, and ideas around the world. I wanted to diversify the three companies I choose to not only show air travel but also show other aspects of this industry such as aircraft manufacturing. To show this industry, I decided to include Boeing, Lockheed Martin, and United Airlines as there are prominent and large companies that respect various aspects of this industry. \n" + //
                        "\n" + //
                        "Since 1998, the aviation industry has faced a lot of challenges and has had many ups and downs both due to the general performance and state of the US economy at the time and due to major events influencing people's travel behaviors. The first major event that affected the aviation industry was the terrorist attacks of 9/11 and the aftermath following them. Demand for air travel decreased significantly which impacted airlines negatively causing Boeing to lose value but Lockheed Martin to increase instead as they received more defense contracts [3]. Over the next couple of years, the industry did very well but in 2008 there was a huge decline due to the Financial Crisis which affected most parts of the economy and not just the Aviation industry. However, after a couple of years, the industry had steady growth and slowly started to boom after 2013. There also was a huge spike in Boeing stock in 2017 due to increased jet sales and record profits [4] \n" + //
                        "\n" + //
                        "However, not everything has gone perfectly in recent years as there was a significant drop in 2020 due to people staying indoors and not traveling as much. Many countries imposed travel restrictions that made it either extremely difficult or impossible for people to travel further reducing the value that aviation companies provide [5].",
                "Shopping and retail/department stores have played a major part throughout U.S. History and its economy for many years. For this industry, I chose Macy's, Walmart, and Nordstrom as they represent different segments within the retail landscape. They are all well-known names in the shopping industry but this industry is rapidly changing over time as we see companies such as Sears go out of business and the rise of e-commerce. \n" + //
                        "\n" + //
                        "The early 2000s were relatively stable for these companies and there wasn't a significant decline after the 9/11 attacks but as with most industries around the US, many companies lost customers during the 2008 Financial Crisis with the notable exception of Walmart which wasn't affected as much and instead saw sales increase [6]. It took some time for it to recover but over the next couple of years, the industry saw slow and steady growth until 2015/2016 in which it took a massive hit due to the explosion in popularity of internet shopping and shifts in consumer preferences and buying habits [7]. This put extreme amounts of pressure on these companies to change how they market themselves and their products. \n" + //
                        "\n" + //
                        "Overall, the shopping and retail industry has changed significantly since 1998 and economic events, shifts in consumer behavior, and the rise of e-commerce have played a major impact on how it performed in recent years. ",
                "The oil industry which is a vital component of the global energy sector has been closely tied to the economic and geopolitical landscape of the U.S. for decades. To represent this industry, I used Exxon, BP, and Chevron as they are household names and are very important in influencing this industry. \n" + //
                        "\n" + //
                        "From 1998 until 2001, the industry did well as a whole but after the attacks on 9/11, the prices and demand for jet fuel fell significantly causing a major decrease in Exxon's and BP's profit and a smaller but still significant drop for Chevron [8]. The industry saw steady growth from then until 2007 but then saw a major drop in 2008 due to the Financial Crisis happening at the time which so far has affected most of the Industries in the U.S. The demand for oil and gas products and a sharp decline in oil prices affected all three companies' revenues and profitability. \n" + //
                        "\n" + //
                        "However, even though most companies, including Exxon and Chevron, eventually recovered from the 2008 crisis and rose to new heights eventually, BP did not. In 2010 and the few years following it, its stock price tanked significantly due to the 2010 Deepwater Horizon Oil Spill which is one of the largest environmental disasters in history [9]. In more recent years, the oil industry has been pretty rocky with pressure due to climate change causing them to invest in renewable energy projects and to explore additional opportunities.",
                "The food and beverage industry was the one that surprised me the most. I originally assumed it would be similar to the ones before it had its ups and downs over time but to my shock, it hasn't had any major impacts from events such as the 9/11 attacks or the 2008 financial collapse which shocked me. Instead, this industry which I represent with Coke, McDonald's, and Starbucks has shown consistent steady growth and profits over the past 25 years which can be attributed to rising yearly profits and revenue even during major events such as the 2008 recession [10]."
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
        String sources = "Sources/Citations:"
        + "\n[1] \"The Impact of Terrorism on Financial Markets\" - IMF Working Paper (2005)" 
        + "\n[2] \"The Report of the Financial Crisis Inquiry Commission\" - National Commission of the Causes of the Financial and Economic Crisis in the United States (2011)"
        + "\n[3] Lockheed Martin 2001 Annual Report and Proxy Statement (2001)"
        + "\n[4] The Boeing Company 2018 Annual Report (2018)"
        + "\n[5] \"COVID-19 and the aviation industry: Impact and policy responses\" - OECD (2020)"
        + "\n[6] \"The economy is tanking. So why is Wal-Mart thriving?\" - Slate.com (2008)"
        + "\n[7] \"Why Retailers Must Restructure In 2016\" - Forbes (2016)"
        + "\n[8] \"COMPANY NEWS; PROFIT FALLS 49% IN THE FOURTH QUARTER AT EXXON MOBIL\" - The New York Times (2002)"
        + "\n[9] \"BP lost 55% shareholder value after the Deepwater Horizon incident\" - Yahoo!news (2014)"
        + "\n[10] \"Is Coca-Cola Stock Recession-Proof?\" - Nasdaq (2020)"
        + "\n[11] \"How COVID-19 has pushed companies over the technology tipping point—and transformed business forever\" - McKinsey & Company (2020)";

        Stage popupStage = new Stage();
        popupStage.setTitle("Sources/Citations");
        VBox popupLayout = new VBox(new Text(sources));
        Scene popupScene = new Scene(popupLayout, 875, 200);
        popupStage.setScene(popupScene);
        popupStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}