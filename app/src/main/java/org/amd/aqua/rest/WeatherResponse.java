package org.amd.aqua.rest;

import java.util.List;

/**
 * Created by Akira on 2018/04/10.
 */

public class WeatherResponse {
    public String base;
    public List<Weather> weather;

    public class Weather {
        public int id;
        public String main;
        public String description;
        public String icon;
    }
}
