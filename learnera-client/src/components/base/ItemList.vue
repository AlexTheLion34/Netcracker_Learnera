<template>
  <v-layout row wrap>
    <v-list v-if="items" two-line>
      <v-list-tile
        v-for="item in items"
        :to="`${apiUrl}/${item.id}`"
        :key="item.id"
        avatar
      >
        <v-list-tile-avatar>
          <v-flex>
            <v-avatar color="teal">
              <img v-if="item.avatar" :src="item.avatar">
              <span v-else class="white--text headline">{{ item.name[0] }}</span>
            </v-avatar>
          </v-flex>
        </v-list-tile-avatar>

        <v-list-tile-content>
          <v-list-tile-title>{{ item.name }}</v-list-tile-title>
          <v-list-tile-sub-title>{{ item.description }}</v-list-tile-sub-title>
        </v-list-tile-content>

        <v-list-tile-action>
          <v-tooltip v-for="(action, index) in actions" :key="index" bottom>
            <v-btn slot="activator" icon ripple @click.prevent="onActionClicked({name: action.name, index})">
              <v-icon :color="action.color || 'grey lighten-1'">{{ action.icon }}</v-icon>
            </v-btn>
            <span>{{ action.name }}</span>
          </v-tooltip>
        </v-list-tile-action>
      </v-list-tile>
    </v-list>
  </v-layout>
</template>

<script>
export default {
  name: 'ItemList',
  props: ['items', 'actions', 'apiUrl'],
  methods: {
    onActionClicked: function(actionInfo) {
      this.$emit('actionClicked', actionInfo);
    }
  }
}
</script>
