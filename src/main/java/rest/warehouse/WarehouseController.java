package rest.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import rest.model.ProductData;
import rest.model.WarehouseData;

import java.util.List;

@RestController
public class WarehouseController {

    @Autowired
    private WarehouseService service;

    @RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
    public String warehouseMain(@RequestParam String warehouseName,
                                @RequestParam String productName,
                                @RequestParam String productCategory) {

        // Default Lagerhaus: Linz
        if (warehouseName == null || warehouseName.isBlank()) {
            warehouseName = "Lagerhaus Linz";
        }

        // Daten je Lagerhaus
        WarehouseData data;
        switch (warehouseName) {
            case "Lagerhaus Wien" -> data = service.getWarehouseData("002");
            case "Lagerhaus Graz" -> data = service.getWarehouseData("003");
            default -> data = service.getWarehouseData("001");
        }

        // Filter anwenden
        if (data.getProduct() != null &&
                ((productName != null && !productName.isBlank()) || (productCategory != null && !productCategory.isBlank()))) {

            String pn = productName != null ? productName.toLowerCase() : "";
            String pc = productCategory != null ? productCategory.toLowerCase() : "";

            List<ProductData> filtered = data.getProduct().stream()
                    .filter(p -> pc.isEmpty() || (p.getProductCategory() != null && p.getProductCategory().toLowerCase().contains(pc)))
                    .filter(p -> pn.isEmpty() || (p.getProductName() != null && p.getProductName().toLowerCase().contains(pn)))
                    .toList();
            data.setProduct(filtered);
        }

        // HTML Ausgabe
        StringBuilder html = new StringBuilder();
        html.append("<html><head><title>Warehouse</title>")
                .append("<style>")
                .append("body { font-family: Arial; margin: 20px; }")
                .append("table { border-collapse: collapse; width: 100%; margin-top: 20px; }")
                .append("th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }")
                .append("th { background-color: #f2f2f2; }")
                .append("input, select { padding: 5px; margin-right: 10px; }")
                .append("button { padding: 5px 10px; margin-right: 10px; }")
                .append("a { text-decoration: none; color: #007bff; }")
                .append("a:hover { text-decoration: underline; }")
                .append("</style></head><body>");

        html.append("<h2>Warehouse Data Viewer</h2>");

        // Dynamische JSON/XML Links
        String warehouseId = switch (warehouseName) {
            case "Lagerhaus Wien" -> "002";
            case "Lagerhaus Graz" -> "003";
            default -> "001";
        };
        html.append("<p>")
                .append("<a href='/warehouse/").append(warehouseId).append("/json'>JSON Ansicht</a> | ")
                .append("<a href='/warehouse/").append(warehouseId).append("/xml'>XML Ansicht</a>")
                .append("</p>");

        // Formular mit Lagerhaus-Auswahl & Filter
        html.append("<form method='get' action='/'>")
                .append("Lagerhaus: <select name='warehouseName'>")
                .append("<option value='Lagerhaus Linz' ").append("Lagerhaus Linz".equals(warehouseName) ? "selected" : "").append(">Lagerhaus Linz</option>")
                .append("<option value='Lagerhaus Wien' ").append("Lagerhaus Wien".equals(warehouseName) ? "selected" : "").append(">Lagerhaus Wien</option>")
                .append("<option value='Lagerhaus Graz' ").append("Lagerhaus Graz".equals(warehouseName) ? "selected" : "").append(">Lagerhaus Graz</option>")
                .append("</select>")
                .append("Produktname: <input type='text' name='productName' value='").append(productName != null ? productName : "").append("'/>")
                .append("Kategorie: <input type='text' name='productCategory' value='").append(productCategory != null ? productCategory : "").append("'/>")
                .append("<button type='submit'>Filtern</button>")
                .append("</form>");

        // Tabelle
        html.append("<h3>").append(data.getWarehouseName()).append(" (ID: ").append(data.getWarehouseID()).append(")</h3>");
        List<ProductData> products = data.getProduct();
        if (products != null && !products.isEmpty()) {
            html.append("<table><tr>")
                    .append("<th>ID</th><th>Name</th><th>Kategorie</th><th>Menge</th><th>Einheit</th></tr>");
            for (ProductData p : products) {
                html.append("<tr>")
                        .append("<td>").append(p.getProductId()).append("</td>")
                        .append("<td>").append(p.getProductName()).append("</td>")
                        .append("<td>").append(p.getProductCategory()).append("</td>")
                        .append("<td>").append(p.getProductQuantity()).append("</td>")
                        .append("<td>").append(p.getProductUnit()).append("</td>")
                        .append("</tr>");
            }
            html.append("</table>");
        } else {
            html.append("<p><i>Keine Produkte gefunden.</i></p>");
        }

        html.append("</body></html>");
        return html.toString();
    }

    // JSON / XML Endpunkte
    @RequestMapping(value = "/warehouse/{inID}/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public WarehouseData warehouseData(@PathVariable String inID) {
        return service.getWarehouseData(inID);
    }

    @RequestMapping(value = "/warehouse/{inID}/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public WarehouseData warehouseDataXML(@PathVariable String inID) {
        return service.getWarehouseData(inID);
    }
}
