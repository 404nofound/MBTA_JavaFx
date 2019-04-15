package json;

import java.util.List;

/**
 *
 * @author Eddy
 */
public class NearbyStopBean {

    private List<IncludedBean> included;
    
    public List<IncludedBean> getIncluded() {
        return included;
    }
    
    public void setIncluded(List<IncludedBean> included) {
        this.included = included;
    }
    
    public static class IncludedBean {

        private AttributesBeanX attributes;
        private String id;
        
        public AttributesBeanX getAttributes() {
            return attributes;
        }

        public void setAttributes(AttributesBeanX attributes) {
            this.attributes = attributes;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public static class AttributesBeanX {
            
            private String name;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}