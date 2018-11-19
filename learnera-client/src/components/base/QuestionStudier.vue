<template>
  <v-container style="padding: 0px 24px 0px 24px;" fill-height>
    <v-layout column wrap>
      <v-flex>
        <v-textarea v-model="question.text" box readonly label="Question text"/>
      </v-flex>
      <v-flex xs12><v-divider/></v-flex>
      <v-flex v-if="question.type === 'fixed' || question.type === 'regex'">
        <v-layout row>
          <v-flex align-center d-flex xs3>
            <v-subheader>Type your answer: </v-subheader>
          </v-flex>
          <v-flex>
            <v-text-field prefix/>
          </v-flex>
        </v-layout>
      </v-flex>
      <v-flex v-else-if="question.type === 'mc'">
        <v-layout row wrap>
          <v-checkbox v-for="(variant, i) in question.variants" 
                      v-model="chosenVariants[i]" :label="variant.choiceText"
                      :key="`v-cb-${i}`" xs4/>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: 'QuestionStudier',
  model: {
    prop: 'question',
    event: 'question-changed'
  },
  props: ['question'],
  data() {
    return {
      chosenVariants: []
    }
  },
  beforeMount() {
    if (this.question.variants) {
      this.chosenVariants = this.question.variants.map(_ => true); 
    }
  },
  methods: {
  }
}
</script>

<style>

</style>
