#comcast
####POST /ad
    Creates new campaign in ad system
        
            request {
                        "partner_id": "unique_partner_id", 
                        "duration", count_seconds, 
                        "ad_content", "ad_content_for_copaign"
                    }
            response {
                    "status": ("sucessful"|"error"), 
                    "message":"detail_for_status_error"
                    }

####GET /ad/{partner_id}
    Gets an active campaign by partnerId        
        Parameters
            active(optional, default: true): 
                true: returns currect active campaign
                false: returns all campaigns by partnerId

        Response 
            one object: {
                            "partner_id": "unique_partner_id", 
                            "duration", count_seconds, 
                            "ad_content", "ad_content_for_copaign",
                            "start_time": "registered_time_in_milliseconds"
                        }
            list of object: [
                            {
                                "partner_id": "unique_partner_id", 
                                "duration", count_seconds, 
                                "ad_content", "ad_content_for_copaign",
                                "start_time": "registered_time_in_milliseconds"
                            },
                            {
                                "partner_id": "unique_partner_id", 
                                "duration", count_seconds, 
                                "ad_content", "ad_content_for_copaign",
                                "start_time": "registered_time_in_milliseconds"
                             }
                        ]
                    
####GET /ad/list                
    Gets all active campaigns in ad system
        Response [
                        {
                            "partner_id": "unique_partner_id", 
                            "duration", count_seconds, 
                            "ad_content", "ad_content_for_copaign",
                            "start_time": "registered_time_in_milliseconds"
                        },
                        {
                            "partner_id": "unique_partner_id", 
                            "duration", count_seconds, 
                            "ad_content", "ad_content_for_copaign",
                            "start_time": "registered_time_in_milliseconds"
                         }
                 ]
