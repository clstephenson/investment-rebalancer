# Investment Mix Re-balancer
![Java CI with Maven](https://github.com/clstephenson/investment-rebalancer/workflows/Java%20CI%20with%20Maven/badge.svg?branch=master)
## This is a console application that is still a work-in-progress.
The goal is to use the application to calculate new investment asset allocations based on current assets
and pre-determined target allocation percentages. I have created, and use a custom Excel workbook, but wanted 
to use this as a personal challenge to re-create it as a Java application.

## Data Persistence
### On exit:
When the exit command is issued by the user, the current context data is serialized and saved as a JSON file in the application's directory.

### On application start:
When the application starts, it looks for the JSON file in the application's directory. If it is present and readable, the program creates a backup copy and then loads the data from the file into the application context object. If the file does not exist, it will start without loading any data.

## Available Commands

### Show a list of the asset holdings.
The asset name is optional and will filter the results. If command is issued without any options, all holdings will 
be listed, along with the actual asset allocation mix across all holdings.
```
holdings
holdings -n [asset name]
```

### Show asset details
Shows detailed information for assets, including their asset mix. The name option is optional, and will filter the list to show a single asset. Without the option, all assets are shown.
```
assets -n [name]
```

### Show list of asset classes
```
classes
```

### Show target asset mix
```
target
```

### Add a new holding
```    
addholding -n [asset name] -s [number of shares]
```  


### Add or Update asset
Update and asset matching the specified name, or create a new asset if it doesn't exist.
```    
updateasset -n [asset name] -p [share price]
``` 

### Update holding
```    
updateholding -i [holding ID from list] -s [number of shares]
``` 

### Update the asset class allocation for an asset
Enter the percentage for each asset class when prompted. The asset name option is required and must match an asset in holdings. Percentages must be between 0 and 100, while the total sum of percentages across classes must equal 100%. Pressing enter without entering a value is the same as entering 0%.
```
updatemix -n [asset name]
```

### Update target asset allocation
Enter the percentage for each asset class when prompted. Percentages must be between 0 and 100, while the total sum of percentages across classes must equal 100%.
```
updatetarget
```

### Delete holding
```    
deleteholding -i [holding number from list]
```
   
### Delete asset
```    
delete -n [asset name]
```

### Show re-balance results.
```
balance
```    

### Exit the program.
```    
exit
```    