Here is some pseudocode to help get you started.

```
// For each inspector inspect the cookie
// If the cookie fails inspection due to size, add to the cookies to be used for crumble and don't continue

// If there aren't any cookie boxes yet, assemble one and add the cookie to it.

// If there already are cookie boxes, find the last cookie box.
// If the last cookie box is full, assemble a new box and add the cookie to it.
// Otherwise add the cookie to the last box.
```