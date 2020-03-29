package com.monsterbuilder.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.monsterbuilder.web.rest.TestUtil;

public class MonsterTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Monster.class);
        Monster monster1 = new Monster();
        monster1.setId(1L);
        Monster monster2 = new Monster();
        monster2.setId(monster1.getId());
        assertThat(monster1).isEqualTo(monster2);
        monster2.setId(2L);
        assertThat(monster1).isNotEqualTo(monster2);
        monster1.setId(null);
        assertThat(monster1).isNotEqualTo(monster2);
    }
}
