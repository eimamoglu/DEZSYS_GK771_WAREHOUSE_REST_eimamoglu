package rest.warehouse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import rest.model.WarehouseData;

@Controller
@RequestMapping("/warehouse/view")
public class WarehouseWebController {
    @Autowired
    private WarehouseService service;

    /**
     * Konstruktor zur Initialisierung des WarehouseService.
     * @param service WarehouseService-Instanz
     */
    public WarehouseWebController(WarehouseService service) {
        this.service = service;
    }

    /**
     * Zeigt die Lagerdaten für das angegebene Lager an.
     * @param warehouseId ID des Lagers
     * @param model Model zum Hinzufügen von Attributen für die Ansicht
     * @return Name der Ansicht zur Anzeige der Lagerdaten
     */
    @GetMapping("{warehouseId}")
    public String viewWarehouse(@PathVariable String warehouseId, Model model) {
        WarehouseData warehouseData = service.getWarehouseData(warehouseId);
        model.addAttribute("warehouse", warehouseData);
        return "warehouse-view";
    }

    /**
     * Filtert die Lagerdaten basierend auf den angegebenen Parametern und zeigt sie an.
     * @param warehouseId ID des Lagers (optional)
     * @param productName Name des Produkts (optional)
     * @param productCategory Kategorie des Produkts (optional)
     * @param model Model zum Hinzufügen von Attributen für die Ansicht
     * @return Name der Ansicht zur Anzeige der gefilterten Lagerdaten
     */
    @GetMapping
    public String warehouseFilter(@RequestParam(required = false) String warehouseId,
                                  @RequestParam(required = false) String productName,
                                  @RequestParam(required = false) String productCategory,
                                  Model model) {
        WarehouseData warehouseData = service.getWarehouseData(productName, productCategory);
        model.addAttribute("warehouse", warehouseData);
        return "warehouse-view";
    }

}