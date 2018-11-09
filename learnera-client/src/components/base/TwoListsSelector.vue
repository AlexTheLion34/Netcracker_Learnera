<template>
  <v-container>
    <v-layout row>
      <v-flex xs6>
        <v-layout column>
          <v-flex>
            <h6 class="title">All groups</h6>
          </v-flex>
          <v-flex>
            <component :is="listRenderer" :items="unselectedItems" :actions="unselectedItemActions"
                       @action-clicked="onUnselectedItemAction"/>
          </v-flex>
        </v-layout>
      </v-flex>
      <v-divider class="mx-2" vertical/>
      <v-flex xs6>
        <v-layout column>
          <v-flex>
            <h6 class="title">Participants</h6>
          </v-flex>
          <v-flex>
            <component :is="listRenderer" :items="selectedItems" :actions="selectedItemActions" 
                       @action-clicked="onSelectedItemAction"/>
          </v-flex>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: 'TwoListSelector',
  props: ['items', 'listRenderer'],
  data() {
    return {
      selectedItems: [],
      unselectedItems: [],
      unselectedItemActions: [
        {name: "Add", icon: "add", color: "green darken-1"}
      ],
      selectedItemActions: [
        {name: "Remove", icon: "remove", color: "red darken-1"}
      ],
    }
  },
  watch: {
    items(val) {
      this.selectedItems = []
      this.unselectedItems = []
      
      if (val) {
        this.unselectedItems = val.slice();
      }
    },
    selectedItems(val) {
      this.$emit('update:selected-items', val);
    },
    unselectedItems(val) {
      this.$emit('update:unselected-items', val);
    }
  },
  beforeMount() {
    this.unselectedItems = this.items ? this.items.slice() : [];
    this.selectedItems = [];
  },
  methods: {
    onUnselectedItemAction({name, index}) {
      const item = this.unselectedItems.splice(index, 1)[0];
      this.selectedItems.push(item);
    },
    onSelectedItemAction({name, index}) {
      const item = this.selectedItems.splice(index, 1)[0];
      this.unselectedItems.push(item);
    },
  }
}
</script>
