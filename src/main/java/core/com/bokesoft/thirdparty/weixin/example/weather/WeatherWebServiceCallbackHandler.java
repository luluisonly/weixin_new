
/**
 * WeatherWebServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package com.bokesoft.thirdparty.weixin.example.weather;

    /**
     *  WeatherWebServiceCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class WeatherWebServiceCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public WeatherWebServiceCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public WeatherWebServiceCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getSupportDataSet method
            * override this method for handling normal response from getSupportDataSet operation
            */
           public void receiveResultgetSupportDataSet(
                    com.bokesoft.thirdparty.weixin.example.weather.WeatherWebServiceStub.GetSupportDataSetResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSupportDataSet operation
           */
            public void receiveErrorgetSupportDataSet(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getWeatherbyCityNamePro method
            * override this method for handling normal response from getWeatherbyCityNamePro operation
            */
           public void receiveResultgetWeatherbyCityNamePro(
                    com.bokesoft.thirdparty.weixin.example.weather.WeatherWebServiceStub.GetWeatherbyCityNameProResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getWeatherbyCityNamePro operation
           */
            public void receiveErrorgetWeatherbyCityNamePro(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getWeatherbyCityName method
            * override this method for handling normal response from getWeatherbyCityName operation
            */
           public void receiveResultgetWeatherbyCityName(
                    com.bokesoft.thirdparty.weixin.example.weather.WeatherWebServiceStub.GetWeatherbyCityNameResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getWeatherbyCityName operation
           */
            public void receiveErrorgetWeatherbyCityName(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getSupportProvince method
            * override this method for handling normal response from getSupportProvince operation
            */
           public void receiveResultgetSupportProvince(
                    com.bokesoft.thirdparty.weixin.example.weather.WeatherWebServiceStub.GetSupportProvinceResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSupportProvince operation
           */
            public void receiveErrorgetSupportProvince(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for getSupportCity method
            * override this method for handling normal response from getSupportCity operation
            */
           public void receiveResultgetSupportCity(
                    com.bokesoft.thirdparty.weixin.example.weather.WeatherWebServiceStub.GetSupportCityResponse result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getSupportCity operation
           */
            public void receiveErrorgetSupportCity(java.lang.Exception e) {
            }
                


    }
    