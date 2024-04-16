Create `Credentials.java` alongside `App.java` with the following contents:
```java
// Credentials.java

package org.example;

public class Credentials {
    public String getDatabaseUsernameString() {
        return "SYSTEM";
    }

    public String getDatabsePasswordString() {
        return "manager";
    }
}
```