# Middleware Engineering "REST and Data Formats"
Verfasser: Elyesa Imamoglu, 4CHIT

## Aufgabenstellung

Ziel des Projekts ist die Entwicklung eines Simulators für Lagerdaten und einer darauf aufsetzenden REST-Schnittstelle. Der Simulator generiert realistische Lagerdaten inklusive Standortinformationen. Diese Daten können über die REST-API im JSON- oder optional im XML-Format abgerufen werden. Zusätzlich soll ein Consumer entwickelt werden, der die Lagerinformationen tabellarisch anzeigt und Filtermöglichkeiten nach Standort oder Produktname bietet.

## Implementierung
#### Build.gradle
- `build.gradle file mit den nötigen implementations.

```groovy
implementation 'jakarta.xml.bind:jakarta.xml.bind-api:4.0.2'
implementation 'com.fasterxml.jackson.dataformat:jackson-dataformat-xml'
implementation 'org.springframework.boot:spring-boot-starter-thymeleaf'
```
#### 1. WarehouseSimulation
- `WarehouseSimulation: Daten nach Warenhäuser wie Linz, Graz, Wien in Switch-case form.

```Java
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
```
#### 2. WarehouseController
- `WarehouseMain mit interner Stringbuilder HTML.

```Java
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
```

#### WarehouseController Teil 2
- `JSON UND XML Endpunkte mit Pfad festgelegt.

```Java
// JSON / XML Endpunkte
@RequestMapping(value = "/warehouse/{inID}/json", produces = MediaType.APPLICATION_JSON_VALUE)
public WarehouseData warehouseData(@PathVariable String inID) {
    return service.getWarehouseData(inID);
}

@RequestMapping(value = "/warehouse/{inID}/xml", produces = MediaType.APPLICATION_XML_VALUE)
public WarehouseData warehouseDataXML(@PathVariable String inID) {
    return service.getWarehouseData(inID);
}
```

## Quellen

[1] „Documentation - Thymeleaf“. Zugegriffen: 5. Oktober 2025. [Online]. Verfügbar unter: [https://www.thymeleaf.org/documentation.html](https://www.thymeleaf.org/documentation.html)

[2] „Getting Started | Building a RESTful Web Service“, Getting Started | Building a RESTful Web Service. Zugegriffen: 5. Oktober 2025. [Online]. Verfügbar unter: [https://spring.io/guides/gs/rest-service](https://spring.io/guides/gs/rest-service)

[3] „Getting Started | Consuming a RESTful Web Service“, Getting Started | Consuming a RESTful Web Service. Zugegriffen: 5. Oktober 2025. [Online]. Verfügbar unter: [https://spring.io/guides/gs/consuming-rest](https://spring.io/guides/gs/consuming-rest)

[4] „Spring Boot“, Spring Boot. Zugegriffen: 5. Oktober 2025. [Online]. Verfügbar unter: [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot)

[5] T. Micheler, _ThomasMicheler/DEZSYS_GK771_WAREHOUSE_REST_. (23. September 2025). Java. Zugegriffen: 5. Oktober 2025. [Online]. Verfügbar unter: [https://github.com/ThomasMicheler/DEZSYS_GK771_WAREHOUSE_REST](https://github.com/ThomasMicheler/DEZSYS_GK771_WAREHOUSE_REST)

[6] Anand, „Answer to ‚How to display content of StringBuilder as HTML?‘“, Stack Overflow. Zugegriffen: 11. November 2025. [Online]. Verfügbar unter: https://stackoverflow.com/a/11609381

[7] B. Genisio, „Answer to ‚How to display content of StringBuilder as HTML?‘“, Stack Overflow. Zugegriffen: 11. November 2025. [Online]. Verfügbar unter: https://stackoverflow.com/a/11605378

[8] MrDuk, „How to display content of StringBuilder as HTML?“, Stack Overflow. Zugegriffen: 11. November 2025. [Online]. Verfügbar unter: https://stackoverflow.com/q/11605292
