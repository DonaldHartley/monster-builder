package com.monsterbuilder.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.monsterbuilder.web.rest.TestUtil;

public class BasetypeTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Basetype.class);
        Basetype basetype1 = new Basetype();
        basetype1.setId(1L);
        Basetype basetype2 = new Basetype();
        basetype2.setId(basetype1.getId());
        assertThat(basetype1).isEqualTo(basetype2);
        basetype2.setId(2L);
        assertThat(basetype1).isNotEqualTo(basetype2);
        basetype1.setId(null);
        assertThat(basetype1).isNotEqualTo(basetype2);
    }
}
