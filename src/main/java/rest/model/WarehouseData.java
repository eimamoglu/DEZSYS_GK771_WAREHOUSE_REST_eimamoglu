package rest.model;

import jakarta.xml.bind.annotation.XmlRootElement;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@XmlRootElement
public class WarehouseData {
	
	private String warehouseID;
	private String warehouseName;
	private String timestamp;
    private String warehouseAddress;
    private String warehousePostalCode;
    private String warehouseCity;
    private String warehouseCountry;
    private List<ProductData> product;

	/**
	 * Constructor
	 */
	public WarehouseData() {
		
		this.timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());

	}
    /**
	 * Setter and Getter Methods
	 */
	public String getWarehouseID() {
		return warehouseID;
	}

	public void setWarehouseID(String warehouseID) {
		this.warehouseID = warehouseID;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

    /**
     * Neue Getter und Setter
     */
    public void setWarehouseAddress(String warehouseAddress) {
        this.warehouseAddress = warehouseAddress;
    }

    public void setWarehousePostalCode(String warehousePostalCode) {
        this.warehousePostalCode = warehousePostalCode;
    }

    public void setWarehouseCity(String warehouseCity) {
        this.warehouseCity = warehouseCity;
    }

    public void setWarehouseCountry(String warehouseCountry) {
        this.warehouseCountry = warehouseCountry;
    }

    public void setProduct(List<ProductData> product) {
        this.product = product;
    }

    public String getWarehouseAddress() {
        return warehouseAddress;
    }

    public String getWarehousePostalCode() {
        return warehousePostalCode;
    }

    public String getWarehouseCity() {
        return warehouseCity;
    }

    public String getWarehouseCountry() {
        return warehouseCountry;
    }

    public List<ProductData> getProduct() {
        return product;
    }

    /**
	 * Methods
	 */
    @Override
    public String toString() {
        return "WarehouseData{" +
                "warehouseID='" + warehouseID + '\'' +
                ", warehouseName='" + warehouseName + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", warehouseAddress='" + warehouseAddress + '\'' +
                ", warehousePostalCode='" + warehousePostalCode + '\'' +
                ", warehouseCity='" + warehouseCity + '\'' +
                ", warehouseCountry='" + warehouseCountry + '\'' +
                ", product=" + product +
                '}';
    }
}
