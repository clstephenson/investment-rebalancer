## This is a console application that is still a work-in-progress.
The goal is to use the application to calculate new investment asset allocations based on current assets
and pre-determined target allocation percentages. I have created, and use a custom Excel workbook, but wanted 
to use this as a personal project to turn it into a Java application.

# Available Commands

Show a list of the asset holdings.

    list
    
Add a new asset.
    
    add -n [asset name] -p [share price] -s [number of shares]
    
Remove an asset from the list of holdings.
    
    delete -i [asset number from list]

Update an asset's information.
    
    update -i [asset ID from list] -n [asset name] -p [share price] -s [number of shares]
    
Show list of asset classes per configuration in asset-classes.txt file.

    classes

Show re-balance results.

    balance
    
Exit the program.
    
    exit