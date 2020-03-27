package domains;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EntityTest {
     private Entity<Long> entity = new Entity<Long>();

    @Before
    public void setUp() throws Exception {
        entity.setId((long)1);
    }
    @Test
    public void getId() {
        assert (entity.getId()==1L);
    }

    @Test
    public void setId() {
        entity.setId(2L);
        assert (entity.getId()==2L);
    }
}