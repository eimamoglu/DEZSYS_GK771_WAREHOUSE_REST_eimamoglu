package rest.warehouse;

import org.springframework.stereotype.Service;
import rest.model.ProductData;
import rest.model.WarehouseData;

import java.util.ArrayList;
import java.util.List;

@Service
public class WarehouseSimulation {
	
	private double getRandomDouble( int inMinimum, int inMaximum ) {

		double number = ( Math.random() * ( (inMaximum-inMinimum) + 1 )) + inMinimum; 
		double rounded = Math.round(number * 100.0) / 100.0; 
		return rounded;
		
	}

	private int getRandomInt( int inMinimum, int inMaximum ) {

		double number = ( Math.random() * ( (inMaximum-inMinimum) + 1 )) + inMinimum; 
		Long rounded = Math.round(number); 
		return rounded.intValue();

	}
	/**
     * Liefert die Daten eines Lagers
     * @param inID ID des Lagers
     * @return Daten des Lagers
     */
    public WarehouseData getData(String inID) {

        WarehouseData data = new WarehouseData();
        List<ProductData> products = new ArrayList<>();

        switch (inID) {
            case "001": // Linz
                data.setWarehouseID("001");
                data.setWarehouseName("Linz Bahnhof");
                data.setWarehouseAddress("Bahnhofsstrasse 27/9");
                data.setWarehousePostalCode("4020");
                data.setWarehouseCity("Linz");
                data.setWarehouseCountry("Austria");

                products.add(new ProductData("00-443175", "Bio Orangensaft Sonne", "Getraenk", 2500, "Packung 1L"));
                products.add(new ProductData("00-871895", "Bio Apfelsaft Gold", "Getraenk", 3420, "Packung 1L"));
                products.add(new ProductData("01-926885", "Ariel Waschmittel Color", "Waschmittel", 478, "Packung 3KG"));
                products.add(new ProductData("00-316253", "Persil Discs Color", "Waschmittel", 1430, "Packung 700G"));
                break;

            case "002": // Wien
                data.setWarehouseID("002");
                data.setWarehouseName("Wien Bahnhof");
                data.setWarehouseAddress("Praterstrasse 15");
                data.setWarehousePostalCode("1020");
                data.setWarehouseCity("Wien");
                data.setWarehouseCountry("Austria");

                products.add(new ProductData("00-554321", "Cola Zero", "Getraenk", 1200, "1L Flasche"));
                products.add(new ProductData("01-778899", "Persil Discs", "Waschmittel", 900, "700G"));
                products.add(new ProductData("00-667788", "Milch 3,5%", "Lebensmittel", 4000, "1L"));
                products.add(new ProductData("00-112233", "Kekse Vollkorn", "Lebensmittel", 1500, "200G"));
                break;

            case "003": // Graz
                data.setWarehouseID("003");
                data.setWarehouseName("Graz Bahnhof");
                data.setWarehouseAddress("Hauptplatz 10");
                data.setWarehousePostalCode("8010");
                data.setWarehouseCity("Graz");
                data.setWarehouseCountry("Austria");

                products.add(new ProductData("00-998877", "Apfelsaft Natur", "Getraenk", 2000, "1L Packung"));
                products.add(new ProductData("01-223344", "Dash Waschmittel", "Waschmittel", 700, "2KG"));
                products.add(new ProductData("00-556677", "Butter", "Lebensmittel", 1000, "250G"));
                products.add(new ProductData("00-334455", "Joghurt Natur", "Lebensmittel", 1200, "150G"));
                break;

            default: // Linz
                return getData("001");
        }

        data.setProduct(products);
        return data;
    }
}
