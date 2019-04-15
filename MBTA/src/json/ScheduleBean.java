package json;

/**
 *
 * @author Eddy
 */
import java.util.List;

public class ScheduleBean {

    private List<DataBeanXXX> data;

    public List<DataBeanXXX> getData() {
        return data;
    }

    public void setData(List<DataBeanXXX> data) {
        this.data = data;
    }

    public static class DataBeanXXX {

        private AttributesBean attributes;

        private RelationshipsBean relationships;

        public AttributesBean getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesBean attributes) {
            this.attributes = attributes;
        }

        public RelationshipsBean getRelationships() {
            return relationships;
        }

        public void setRelationships(RelationshipsBean relationships) {
            this.relationships = relationships;
        }

        public static class AttributesBean {

            private String arrival_time;
            private String departure_time;

            public String getArrival_time() {
                return arrival_time;
            }

            public void setArrival_time(String arrival_time) {
                this.arrival_time = arrival_time;
            }

            public String getDeparture_time() {
                return departure_time;
            }

            public void setDeparture_time(String departure_time) {
                this.departure_time = departure_time;
            }

        }

        public static class RelationshipsBean {

            private RouteBean route;

            public RouteBean getRoute() {
                return route;
            }

            public void setRoute(RouteBean route) {
                this.route = route;
            }

            public static class RouteBean {

                private DataBean data;

                public DataBean getData() {
                    return data;
                }

                public void setData(DataBean data) {
                    this.data = data;
                }

                public static class DataBean {

                    private String id;
                    private String type;

                    public String getId() {
                        return id;
                    }

                    public void setId(String id) {
                        this.id = id;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }
                }
            }
        }
    }
}