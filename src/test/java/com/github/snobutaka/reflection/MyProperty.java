package com.github.snobutaka.reflection;

public class MyProperty {
    int intProp;
    long longProp;
    boolean booleanProp;
    String stringProp;
    InnerProperty innerProp;

    public MyProperty() {
        this.setInnerProp(new InnerProperty());
    }

    public int getIntProp() {
        return intProp;
    }

    public void setIntProp(int intProp) {
        this.intProp = intProp;
    }

    public long getLongProp() {
        return longProp;
    }

    public void setLongProp(long longProp) {
        this.longProp = longProp;
    }


    public boolean isBooleanProp() {
        return booleanProp;
    }

    public void setBooleanProp(boolean booleanProp) {
        this.booleanProp = booleanProp;
    }

    public String getStringProp() {
        return stringProp;
    }

    public void setStringProp(String stringProp) {
        this.stringProp = stringProp;
    }

    public InnerProperty getInnerProp() {
        return innerProp;
    }

    public void setInnerProp(InnerProperty innerProp) {
        this.innerProp = innerProp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (booleanProp ? 1231 : 1237);
        result = prime * result + ((innerProp == null) ? 0 : innerProp.hashCode());
        result = prime * result + intProp;
        result = prime * result + (int) (longProp ^ (longProp >>> 32));
        result = prime * result + ((stringProp == null) ? 0 : stringProp.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MyProperty other = (MyProperty) obj;
        if (booleanProp != other.booleanProp) {
            return false;
        }
        if (innerProp == null) {
            if (other.innerProp != null) {
                return false;
            }
        } else if (!innerProp.equals(other.innerProp)) {
            return false;
        }
        if (intProp != other.intProp) {
            return false;
        }
        if (longProp != other.longProp) {
            return false;
        }
        if (stringProp == null) {
            if (other.stringProp != null) {
                return false;
            }
        } else if (!stringProp.equals(other.stringProp)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("MyProperty [intProp=");
        builder.append(intProp);
        builder.append(", longProp=");
        builder.append(longProp);
        builder.append(", booleanProp=");
        builder.append(booleanProp);
        builder.append(", stringProp=");
        builder.append(stringProp);
        builder.append(", innerProp=");
        builder.append(innerProp);
        builder.append("]");
        return builder.toString();
    }

    public static class InnerProperty {
        int intProp;
        long longProp;
        boolean booleanProp;
        String stringProp;

        public int getIntProp() {
            return intProp;
        }

        public void setIntProp(int intProp) {
            this.intProp = intProp;
        }

        public long getLongProp() {
            return longProp;
        }

        public void setLongProp(long longProp) {
            this.longProp = longProp;
        }

        public String getStringProp() {
            return stringProp;
        }

        public void setStringProp(String stringProp) {
            this.stringProp = stringProp;
        }

        public boolean isBooleanProp() {
            return booleanProp;
        }

        public void setBooleanProp(boolean booleanProp) {
            this.booleanProp = booleanProp;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("InnerProperty [intProp=");
            builder.append(intProp);
            builder.append(", longProp=");
            builder.append(longProp);
            builder.append(", booleanProp=");
            builder.append(booleanProp);
            builder.append(", stringProp=");
            builder.append(stringProp);
            builder.append("]");
            return builder.toString();
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + (booleanProp ? 1231 : 1237);
            result = prime * result + intProp;
            result = prime * result + (int) (longProp ^ (longProp >>> 32));
            result = prime * result + ((stringProp == null) ? 0 : stringProp.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            InnerProperty other = (InnerProperty) obj;
            if (booleanProp != other.booleanProp) {
                return false;
            }
            if (intProp != other.intProp) {
                return false;
            }
            if (longProp != other.longProp) {
                return false;
            }
            if (stringProp == null) {
                if (other.stringProp != null) {
                    return false;
                }
            } else if (!stringProp.equals(other.stringProp)) {
                return false;
            }
            return true;
        }
    }
}
