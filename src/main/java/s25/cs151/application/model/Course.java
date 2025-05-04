package s25.cs151.application.model;

public class Course {
    private String code;
    private String name;
    private String section;

    public Course(String code, String name, String section) {
        this.code = code;
        this.name = name;
        this.section = section;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getSection() {
        return section;
    }

    // Optional setters if you want them
    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSection(String section) {
        this.section = section;
    }

    /**
     * Two Courses are considered duplicates if
     * code, name, and section are all the same.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Course)) return false;
        Course other = (Course) obj;
        return this.code.equalsIgnoreCase(other.code)
                && this.name.equalsIgnoreCase(other.name)
                && this.section.equalsIgnoreCase(other.section);
    }

    @Override
    public int hashCode() {
        return (code + name + section).toLowerCase().hashCode();
    }
    
    /**
     * Returns a string representation of this Course.
     * @return A string in the format "CODE: NAME (SECTION)"
     */
    @Override
    public String toString() {
        return code + ": " + name + " (" + section + ")";
    }
} 