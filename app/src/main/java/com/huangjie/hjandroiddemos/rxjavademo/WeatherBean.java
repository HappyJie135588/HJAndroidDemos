package com.huangjie.hjandroiddemos.rxjavademo;

/**
 * Created by HuangJie on 2017/7/17.
 */

public class WeatherBean {
    @Override
    public String toString() {
        return "WeatherBean{" +
                "resultcode='" + resultcode + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                ", error_code=" + error_code +
                '}';
    }

    /**
     * resultcode : 200
     * reason : successed!
     * result : {"sk":{"temp":"24","wind_direction":"东风","wind_strength":"1级","humidity":"95%","time":"09:05"},"today":{"temperature":"23℃~30℃","weather":"小雨转大雨","weather_id":{"fa":"07","fb":"09"},"wind":"微风","week":"星期一","city":"成都","date_y":"2017年07月17日","dressing_index":"热","dressing_advice":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。","uv_index":"弱","comfort_index":"","wash_index":"不宜","travel_index":"较不宜","exercise_index":"较不宜","drying_index":""},"future":{"day_20170717":{"temperature":"23℃~30℃","weather":"小雨转大雨","weather_id":{"fa":"07","fb":"09"},"wind":"微风","week":"星期一","date":"20170717"},"day_20170718":{"temperature":"24℃~31℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"微风","week":"星期二","date":"20170718"},"day_20170719":{"temperature":"24℃~30℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"微风","week":"星期三","date":"20170719"},"day_20170720":{"temperature":"23℃~31℃","weather":"小雨","weather_id":{"fa":"07","fb":"07"},"wind":"微风","week":"星期四","date":"20170720"},"day_20170721":{"temperature":"23℃~31℃","weather":"小雨","weather_id":{"fa":"07","fb":"07"},"wind":"微风","week":"星期五","date":"20170721"},"day_20170722":{"temperature":"24℃~31℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"微风","week":"星期六","date":"20170722"},"day_20170723":{"temperature":"24℃~31℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"微风","week":"星期日","date":"20170723"}}}
     * error_code : 0
     */

    private String resultcode;
    private String reason;
    /**
     * sk : {"temp":"24","wind_direction":"东风","wind_strength":"1级","humidity":"95%","time":"09:05"}
     * today : {"temperature":"23℃~30℃","weather":"小雨转大雨","weather_id":{"fa":"07","fb":"09"},"wind":"微风","week":"星期一","city":"成都","date_y":"2017年07月17日","dressing_index":"热","dressing_advice":"天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。","uv_index":"弱","comfort_index":"","wash_index":"不宜","travel_index":"较不宜","exercise_index":"较不宜","drying_index":""}
     * future : {"day_20170717":{"temperature":"23℃~30℃","weather":"小雨转大雨","weather_id":{"fa":"07","fb":"09"},"wind":"微风","week":"星期一","date":"20170717"},"day_20170718":{"temperature":"24℃~31℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"微风","week":"星期二","date":"20170718"},"day_20170719":{"temperature":"24℃~30℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"微风","week":"星期三","date":"20170719"},"day_20170720":{"temperature":"23℃~31℃","weather":"小雨","weather_id":{"fa":"07","fb":"07"},"wind":"微风","week":"星期四","date":"20170720"},"day_20170721":{"temperature":"23℃~31℃","weather":"小雨","weather_id":{"fa":"07","fb":"07"},"wind":"微风","week":"星期五","date":"20170721"},"day_20170722":{"temperature":"24℃~31℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"微风","week":"星期六","date":"20170722"},"day_20170723":{"temperature":"24℃~31℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"微风","week":"星期日","date":"20170723"}}
     */

    private ResultBean result;
    private int error_code;

    public String getResultcode() {
        return resultcode;
    }

    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * temp : 24
         * wind_direction : 东风
         * wind_strength : 1级
         * humidity : 95%
         * time : 09:05
         */

        private SkBean     sk;
        /**
         * temperature : 23℃~30℃
         * weather : 小雨转大雨
         * weather_id : {"fa":"07","fb":"09"}
         * wind : 微风
         * week : 星期一
         * city : 成都
         * date_y : 2017年07月17日
         * dressing_index : 热
         * dressing_advice : 天气热，建议着短裙、短裤、短薄外套、T恤等夏季服装。
         * uv_index : 弱
         * comfort_index :
         * wash_index : 不宜
         * travel_index : 较不宜
         * exercise_index : 较不宜
         * drying_index :
         */

        private TodayBean  today;
        /**
         * day_20170717 : {"temperature":"23℃~30℃","weather":"小雨转大雨","weather_id":{"fa":"07","fb":"09"},"wind":"微风","week":"星期一","date":"20170717"}
         * day_20170718 : {"temperature":"24℃~31℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"微风","week":"星期二","date":"20170718"}
         * day_20170719 : {"temperature":"24℃~30℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"微风","week":"星期三","date":"20170719"}
         * day_20170720 : {"temperature":"23℃~31℃","weather":"小雨","weather_id":{"fa":"07","fb":"07"},"wind":"微风","week":"星期四","date":"20170720"}
         * day_20170721 : {"temperature":"23℃~31℃","weather":"小雨","weather_id":{"fa":"07","fb":"07"},"wind":"微风","week":"星期五","date":"20170721"}
         * day_20170722 : {"temperature":"24℃~31℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"微风","week":"星期六","date":"20170722"}
         * day_20170723 : {"temperature":"24℃~31℃","weather":"中雨转小雨","weather_id":{"fa":"08","fb":"07"},"wind":"微风","week":"星期日","date":"20170723"}
         */

        private FutureBean future;

        public SkBean getSk() {
            return sk;
        }

        public void setSk(SkBean sk) {
            this.sk = sk;
        }

        public TodayBean getToday() {
            return today;
        }

        public void setToday(TodayBean today) {
            this.today = today;
        }

        public FutureBean getFuture() {
            return future;
        }

        public void setFuture(FutureBean future) {
            this.future = future;
        }

        public static class SkBean {
            private String temp;
            private String wind_direction;
            private String wind_strength;
            private String humidity;
            private String time;

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getWind_direction() {
                return wind_direction;
            }

            public void setWind_direction(String wind_direction) {
                this.wind_direction = wind_direction;
            }

            public String getWind_strength() {
                return wind_strength;
            }

            public void setWind_strength(String wind_strength) {
                this.wind_strength = wind_strength;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class TodayBean {
            private String temperature;
            private String weather;
            /**
             * fa : 07
             * fb : 09
             */

            private WeatherIdBean weather_id;
            private String wind;
            private String week;
            private String city;
            private String date_y;
            private String dressing_index;
            private String dressing_advice;
            private String uv_index;
            private String comfort_index;
            private String wash_index;
            private String travel_index;
            private String exercise_index;
            private String drying_index;

            public String getTemperature() {
                return temperature;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public WeatherIdBean getWeather_id() {
                return weather_id;
            }

            public void setWeather_id(WeatherIdBean weather_id) {
                this.weather_id = weather_id;
            }

            public String getWind() {
                return wind;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getDate_y() {
                return date_y;
            }

            public void setDate_y(String date_y) {
                this.date_y = date_y;
            }

            public String getDressing_index() {
                return dressing_index;
            }

            public void setDressing_index(String dressing_index) {
                this.dressing_index = dressing_index;
            }

            public String getDressing_advice() {
                return dressing_advice;
            }

            public void setDressing_advice(String dressing_advice) {
                this.dressing_advice = dressing_advice;
            }

            public String getUv_index() {
                return uv_index;
            }

            public void setUv_index(String uv_index) {
                this.uv_index = uv_index;
            }

            public String getComfort_index() {
                return comfort_index;
            }

            public void setComfort_index(String comfort_index) {
                this.comfort_index = comfort_index;
            }

            public String getWash_index() {
                return wash_index;
            }

            public void setWash_index(String wash_index) {
                this.wash_index = wash_index;
            }

            public String getTravel_index() {
                return travel_index;
            }

            public void setTravel_index(String travel_index) {
                this.travel_index = travel_index;
            }

            public String getExercise_index() {
                return exercise_index;
            }

            public void setExercise_index(String exercise_index) {
                this.exercise_index = exercise_index;
            }

            public String getDrying_index() {
                return drying_index;
            }

            public void setDrying_index(String drying_index) {
                this.drying_index = drying_index;
            }

            public static class WeatherIdBean {
                private String fa;
                private String fb;

                public String getFa() {
                    return fa;
                }

                public void setFa(String fa) {
                    this.fa = fa;
                }

                public String getFb() {
                    return fb;
                }

                public void setFb(String fb) {
                    this.fb = fb;
                }
            }
        }

        public static class FutureBean {
            /**
             * temperature : 23℃~30℃
             * weather : 小雨转大雨
             * weather_id : {"fa":"07","fb":"09"}
             * wind : 微风
             * week : 星期一
             * date : 20170717
             */

            private Day20170717Bean day_20170717;
            /**
             * temperature : 24℃~31℃
             * weather : 中雨转小雨
             * weather_id : {"fa":"08","fb":"07"}
             * wind : 微风
             * week : 星期二
             * date : 20170718
             */

            private Day20170718Bean day_20170718;
            /**
             * temperature : 24℃~30℃
             * weather : 中雨转小雨
             * weather_id : {"fa":"08","fb":"07"}
             * wind : 微风
             * week : 星期三
             * date : 20170719
             */

            private Day20170719Bean day_20170719;
            /**
             * temperature : 23℃~31℃
             * weather : 小雨
             * weather_id : {"fa":"07","fb":"07"}
             * wind : 微风
             * week : 星期四
             * date : 20170720
             */

            private Day20170720Bean day_20170720;
            /**
             * temperature : 23℃~31℃
             * weather : 小雨
             * weather_id : {"fa":"07","fb":"07"}
             * wind : 微风
             * week : 星期五
             * date : 20170721
             */

            private Day20170721Bean day_20170721;
            /**
             * temperature : 24℃~31℃
             * weather : 中雨转小雨
             * weather_id : {"fa":"08","fb":"07"}
             * wind : 微风
             * week : 星期六
             * date : 20170722
             */

            private Day20170722Bean day_20170722;
            /**
             * temperature : 24℃~31℃
             * weather : 中雨转小雨
             * weather_id : {"fa":"08","fb":"07"}
             * wind : 微风
             * week : 星期日
             * date : 20170723
             */

            private Day20170723Bean day_20170723;

            public Day20170717Bean getDay_20170717() {
                return day_20170717;
            }

            public void setDay_20170717(Day20170717Bean day_20170717) {
                this.day_20170717 = day_20170717;
            }

            public Day20170718Bean getDay_20170718() {
                return day_20170718;
            }

            public void setDay_20170718(Day20170718Bean day_20170718) {
                this.day_20170718 = day_20170718;
            }

            public Day20170719Bean getDay_20170719() {
                return day_20170719;
            }

            public void setDay_20170719(Day20170719Bean day_20170719) {
                this.day_20170719 = day_20170719;
            }

            public Day20170720Bean getDay_20170720() {
                return day_20170720;
            }

            public void setDay_20170720(Day20170720Bean day_20170720) {
                this.day_20170720 = day_20170720;
            }

            public Day20170721Bean getDay_20170721() {
                return day_20170721;
            }

            public void setDay_20170721(Day20170721Bean day_20170721) {
                this.day_20170721 = day_20170721;
            }

            public Day20170722Bean getDay_20170722() {
                return day_20170722;
            }

            public void setDay_20170722(Day20170722Bean day_20170722) {
                this.day_20170722 = day_20170722;
            }

            public Day20170723Bean getDay_20170723() {
                return day_20170723;
            }

            public void setDay_20170723(Day20170723Bean day_20170723) {
                this.day_20170723 = day_20170723;
            }

            public static class Day20170717Bean {
                private String temperature;
                private String weather;
                /**
                 * fa : 07
                 * fb : 09
                 */

                private WeatherIdBean weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBean getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBean weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBean {
                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20170718Bean {
                private String temperature;
                private String weather;
                /**
                 * fa : 08
                 * fb : 07
                 */

                private WeatherIdBean weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBean getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBean weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBean {
                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20170719Bean {
                private String temperature;
                private String weather;
                /**
                 * fa : 08
                 * fb : 07
                 */

                private WeatherIdBean weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBean getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBean weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBean {
                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20170720Bean {
                private String temperature;
                private String weather;
                /**
                 * fa : 07
                 * fb : 07
                 */

                private WeatherIdBean weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBean getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBean weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBean {
                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20170721Bean {
                private String temperature;
                private String weather;
                /**
                 * fa : 07
                 * fb : 07
                 */

                private WeatherIdBean weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBean getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBean weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBean {
                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20170722Bean {
                private String temperature;
                private String weather;
                /**
                 * fa : 08
                 * fb : 07
                 */

                private WeatherIdBean weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBean getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBean weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBean {
                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }

            public static class Day20170723Bean {
                private String temperature;
                private String weather;
                /**
                 * fa : 08
                 * fb : 07
                 */

                private WeatherIdBean weather_id;
                private String wind;
                private String week;
                private String date;

                public String getTemperature() {
                    return temperature;
                }

                public void setTemperature(String temperature) {
                    this.temperature = temperature;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public WeatherIdBean getWeather_id() {
                    return weather_id;
                }

                public void setWeather_id(WeatherIdBean weather_id) {
                    this.weather_id = weather_id;
                }

                public String getWind() {
                    return wind;
                }

                public void setWind(String wind) {
                    this.wind = wind;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public static class WeatherIdBean {
                    private String fa;
                    private String fb;

                    public String getFa() {
                        return fa;
                    }

                    public void setFa(String fa) {
                        this.fa = fa;
                    }

                    public String getFb() {
                        return fb;
                    }

                    public void setFb(String fb) {
                        this.fb = fb;
                    }
                }
            }
        }
    }
}
