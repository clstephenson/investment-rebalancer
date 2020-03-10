# Investment Mix Re-balancer
## This is a console application that is still a work-in-progress.
The goal is to use the application to calculate new investment asset allocations based on current assets
and pre-determined target allocation percentages. I have created, and use a custom Excel workbook, but wanted 
to use this as a personal project to turn it into a Java application.

## Available Commands

### Show a list of the asset holdings.
The asset name is optional and will filter the results. If command is issued without any options, all holdings will 
be listed.
```
holdings
holdings -n [asset name]
```
### Show list of asset classes
```
classes
```
### Show asset details
shows name, symbol, price, total shares of asset in holdings, and asset mix. The name option is optional, and will filter the list to show a single asset. Without the option, all assets are shown.
```
assets -n [name]
```
### Add a new holding
```    
add -n [asset name] -p [share price] -s [number of shares]
```    
### Remove holding
```    
delete -i [asset number from list]
```
### Update asset or holding information
```    
update -i [asset ID from list] -n [asset name] -p [share price] -s [number of shares]
```    
### Update the asset class mix for an asset
Enter the percentage for each asset class when prompted. The asset name option is required and must match an asset in holdings. Percentages must be between 0 and 100, while the total sum of percentages across classes must equal 100%.
```
updatemix -n [asset name]
```
### Show re-balance results.
```
balance
```    
### Exit the program.
```    
exit
```    