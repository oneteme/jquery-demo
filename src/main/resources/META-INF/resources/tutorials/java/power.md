	```java
    default Column powPrice() {
		return price().pow(2);
	}

    powPrice();
    ```