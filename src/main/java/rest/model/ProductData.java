package rest.model;

import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductData {

    private String productId;
    private String productName;
    private String productCategory;
    private int productQuantity;
    private String productUnit;

    public ProductData() {
    }

    public ProductData(String productId, String productName, String productCategory, int productQuantity, String productUnit) {
        this.productId = productId;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productQuantity = productQuantity;
        this.productUnit = productUnit;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setProductUnit(String productUnit) {
        this.productUnit = productUnit;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public String getProductUnit() {
        return productUnit;
    }

    @Override
    public String toString() {
        return "ProductData{" +
                "productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", productCategory='" + productCategory + '\'' +
                ", productQuantity=" + productQuantity +
                ", productUnit='" + productUnit + '\'' +
                '}';
    }
}
