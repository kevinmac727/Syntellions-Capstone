package domain;

public class Menu {
	String id;
	String name;
	char vegetarian;
	String description;
	String type;
	String slot_ID;
	String photo;
	float price;
	
	public Menu(String id, String name, char vegetarian, String type, String description, String slot_ID, String photo, float price) {
		super();
		this.id = id;
		this.name = name;
		this.vegetarian = vegetarian;
		this.type = type;
		this.description = description;
		this.slot_ID = slot_ID;
		this.photo = photo;
		this.price = price;
	}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public char getVegetarian() {
		return vegetarian;
	}

	public void setVegetarian(char vegetarian) {
		this.vegetarian = vegetarian;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSlot_ID() {
		return slot_ID;
	}

	public void setSlot_ID(String slot_ID) {
		this.slot_ID = slot_ID;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}


	@Override
	public String toString() {
		return "Menu [id=" + id + ", name=" + name + ", vegetarian=" + vegetarian + ", description=" + description
				+ ", type=" + type + ", slot_ID=" + slot_ID + ", photo=" + photo + ", price=" + price + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((photo == null) ? 0 : photo.hashCode());
		result = prime * result + Float.floatToIntBits(price);
		result = prime * result + ((slot_ID == null) ? 0 : slot_ID.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + vegetarian;
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menu other = (Menu) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (photo == null) {
			if (other.photo != null)
				return false;
		} else if (!photo.equals(other.photo))
			return false;
		if (Float.floatToIntBits(price) != Float.floatToIntBits(other.price))
			return false;
		if (slot_ID == null) {
			if (other.slot_ID != null)
				return false;
		} else if (!slot_ID.equals(other.slot_ID))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (vegetarian != other.vegetarian)
			return false;
		return true;
	}

	
	
}

