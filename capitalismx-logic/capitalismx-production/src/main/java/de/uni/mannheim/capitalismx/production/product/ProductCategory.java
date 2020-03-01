package de.uni.mannheim.capitalismx.production.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.uni.mannheim.capitalismx.procurement.component.ComponentCategory;

/**
 * The enum Product category.
 *
 * @author dzhao
 */
public enum ProductCategory implements Serializable {

	NOTEBOOK("Notebook"),
	PHONE("Phone"),
	GAME_BOY("Game Boy"),
	TELEVISION("Television");

	/**
	 * Instantiates the product category.
	 *
	 * @param productName
	 */
	ProductCategory(String productName) {
		this.productName = productName;
	}

	private String productName;

	@Override
	public String toString() {
		return this.productName;
	}

	/**
	 * Returns the {@link ProductCategory} a given {@link ComponentCategory} belongs
	 * to.
	 *
	 * @param componentCategory The {@link ComponentCategory} to check.
	 * @return The owning {@link ProductCategory}.
	 */
	public static ProductCategory getProductCategoryForComponentCategory(ComponentCategory componentCategory) {
		if (componentCategory.name().startsWith("G_")) {
			return ProductCategory.GAME_BOY;
		} else if (componentCategory.name().startsWith("N_")) {
			return ProductCategory.NOTEBOOK;
		} else if (componentCategory.name().startsWith("P_")) {
			return ProductCategory.PHONE;
		} else if (componentCategory.name().startsWith("T_")) {
			return ProductCategory.TELEVISION;
		} else {
			return null;
		}
	}

	/**
	 * Gets component categories.
	 *
	 * @param productCategory the product category
	 * @return the component categories
	 */
	public static List<ComponentCategory> getComponentCategories(ProductCategory productCategory) {
		List<ComponentCategory> categories = new ArrayList<ComponentCategory>();
		for (ComponentCategory componentCategory : ComponentCategory.values()) {
			if (componentCategory.name().startsWith("G_") && productCategory == ProductCategory.GAME_BOY ) {
				categories.add(componentCategory);
			} else if (componentCategory.name().startsWith("N_") && productCategory == ProductCategory.NOTEBOOK) {
				categories.add(componentCategory);
			} else if (componentCategory.name().startsWith("P_") && productCategory == ProductCategory.PHONE) {
				categories.add(componentCategory);
			} else if (componentCategory.name().startsWith("T_") && productCategory == ProductCategory.TELEVISION) {
				categories.add(componentCategory);
			} else {
				continue;
			}
		}
		return categories;
	}

	/**
	 * Gets product category by name.
	 *
	 * @param name The name of the {@link ProductCategory}.
	 * @return Returns the {@link ProductCategory} with the matching name.
	 * @author duly
	 */
	public static ProductCategory getProductCategoryByName(String name) {
		ProductCategory[] productCategories = ProductCategory.values();

		for(ProductCategory productCategory : productCategories) {
			if(productCategory.productName.equals(name)) {
				return productCategory;
			}
		}
		return null;
	}
}
