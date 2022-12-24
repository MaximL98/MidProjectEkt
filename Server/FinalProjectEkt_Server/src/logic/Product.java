package logic;
/**
 * Project Name: finalProjectEkt_Server
 * Logic class that contains the details needed to save up for each report.
 * @author Maxim Lebedinsky
 * @version 16/12/2022
 */
public class Product {
	/**
	*Product logic part.
	*private fields that will contain product's: id, name and cost per unit
	*/
	private String productID; //String cuz maybe => id = A12
	private String productName;
	private String costPerUnit;//String cuz maybe => costPerUnit = 10NIS
	/**
	 * Product constructor.
	 * @param productID
	 * @param productName
	 * @param costPerUnit
	 */
	public Product(String productID, String productName, String costPerUnit) {
		super();
		this.productID = productID;
		this.productName = productName;
		this.costPerUnit = costPerUnit;
	}
	/**
	 * getting the id of a product
	 * @return productID
	 */
	public String getProductID() {
		return productID;
	}
	/**
	 * setting the id of a product
	 * @param productID
	 */
	public void setProductID(String productID) {
		this.productID = productID;
	}
	/**
	 * getting product name
	 * @return productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * setting product name
	 * @param productName
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * getting product cost per unit
	 * @return costPerUnit
	 */
	public String getCostPerUnit() {
		return costPerUnit;
	}
	/**
	 * setting product cost per unit
	 * @param costPerUnit
	 */
	public void setCostPerUnit(String costPerUnit) {
		this.costPerUnit = costPerUnit;
	}
	/**
	 * toString method, returns report details
	 */
	@Override
	public String toString() {
		return "Product [productID=" + productID + ", productName=" + productName + ", costPerUnit=" + costPerUnit
				+ "]";
	}
	
	
	
}
