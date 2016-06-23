# Agri Loan

# Problem Description
Agri-Loan is one of the loan products and needs some specific configuration different from other loan-products.

# Major Changes

## Need to add new Reference type as Landlord.

    ## Data Model change
        - crops_cultivated (String)
        - area_of_land_for_cultivation (double)
        - Charges (double) --extra
    ### Config change
        - "extra":{
        {
                    "label": "Payment type",
                    "path": "reference_check.extra.payment_mode",
                    "input": "dropdown",
                    "options": [
                                  "In cash",
                                  "In crops"
                                ]
        },
            "reference_check.crops_cultivated": {
                            "visible": true,
                                            "order": 46
                                                          
            },
            "reference_check.are_of_land_for_cultivation": {
                             "visible": true,
                                             "order": 47
                                                           
            },
            "reference_check.Crops cultivated": {
                            "visible": true,
                                            "order": 47
                                                          
            },
        }
## Need to add new Investment plan
    ### Data model change
        - description (String)
        - frequency (integer)
        - purchase_price (double)
    ### Config change
        "investment_plan": {
            "category": {
                "-default": {
                                "visible": true,
                                "optional": false,
                                "label": "Investment Plan",
                                "-data": {
                                          "text": true,
                                            "type": true,
                                          "value": true,
                                        "completion_date": false 
                            }
                                              
                },
                    {
                        "agricultural":
                        {
                          "-data":
                          {
                           "description":true,
                           "value":
                           {
                             "visible":true,
                           "label":"amount"
                            },
                           "frequency":true,
                           "purchase_price":true
                         }
                                                          
                    }
                }                 
            }
        }

## Need to add new IncomeSheets.
