package rest.warehouse;

import org.springframework.stereotype.Service;
import rest.model.ProductData;
import rest.model.WarehouseData;

import java.util.List;

@Service
public class WarehouseService {

    /**
     * Liefert einen Grußtext zurueck.
     * @param inModule Name des Moduls
     * @return Grußtext
     */
	public String getGreetings( String inModule ) {
        return "Greetings from " + inModule;
    }

    /**
     * Liefert die Daten des Lagers mit der angegebenen ID zurueck.
     * @param inID ID des Lagers
     * @return Lagerdaten
     */
    public WarehouseData getWarehouseData( String inID ) {

    	WarehouseSimulation simulation = new WarehouseSimulation();
        return simulation.getData( inID );
        
    }

    /**
     * Liefert die Daten des Lagers mit den angegebenen Filterkriterien zurueck.
     * @param productName Name des Produkts
     * @param productCategory Kategorie des Produkts
     * @return Gefilterte Lagerdaten
     */
    public WarehouseData getWarehouseData(String productName, String productCategory) {
        WarehouseData data = getWarehouseData("001");

        if (data.getProduct() != null) {
            List<ProductData> filtern = data.getProduct().stream()
                    .filter(p -> productCategory == null || productCategory.isEmpty() || p.getProductCategory().toLowerCase().contains(productCategory.toLowerCase()))
                    .filter(p -> productName == null || productName.isEmpty() || p.getProductName().toLowerCase().contains(productName.toLowerCase())).toList();
            data.setProduct(filtern);
        }
        return data;
    }

}