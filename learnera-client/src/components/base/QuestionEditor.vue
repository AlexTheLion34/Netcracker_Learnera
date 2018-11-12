<template>
  <v-container style="padding: 0px 24px 0px 24px;" fill-height>
    <v-layout fill-height row wrap>
      <v-flex xs8>
        <v-textarea :readonly="!editable" v-model="question.questionText" label="Question text"/>
      </v-flex>
      <v-flex fill-height xs4>
        <v-radio-group :readonly="!editable" v-model="question.type" label>
          <div slot="label" class="pl-0"><strong>Question</strong> type:</div>
          <v-radio label="Fixed answer" value="fixed"/>
          <v-radio label="Regex" value="regex"/>
          <v-radio label="Multiple choice" value="mc"/>
        </v-radio-group>
      </v-flex>
      <v-flex xs12><v-divider style="margin: -0.5em 0 0.5em 0;"/></v-flex>
      <v-flex v-if="question.type === 'fixed' || question.type === 'regex'" xs12>
        <v-layout row>
          <v-flex align-center d-flex xs3>
            <v-subheader>Question answer: </v-subheader>
          </v-flex>
          <v-flex>
            <v-text-field :readonly="!editable" v-model="question.answer" prefix/>
          </v-flex>
        </v-layout>
      </v-flex>
      <v-flex v-else-if="question.type === 'mc'" xs12>
        <v-btn v-if="editable" @click="addVariant">
          Add variant
        </v-btn>
        <v-layout column>
          <v-layout v-for="(variant, i) in question.variants" :key="`v-${i}`" 
                    justify-start align-center row>
            <div v-if="editable">
              <v-btn small style="margin-top: 0px; margin-bottom: 0px; margin-left: -5px;" flat icon @click="removeVariant(i)">
                <v-icon color="red darken-4">remove</v-icon>
              </v-btn>
            </div>
            <v-flex xs4>
              <v-text-field :readonly="!editable" v-model="variant.choiceText" label="Variant text" />
            </v-flex>
            <v-flex xs4>
              <v-checkbox :readonly="!editable" v-model="variant.correct" label="Is correct?"/>
            </v-flex>
          </v-layout>
        </v-layout>
      </v-flex>
      <v-flex xs12><v-divider style="margin: 0.5em 0 0em 0;"/></v-flex>
      <v-flex xs12>
        <v-subheader class="pl-0"><div><strong>Points</strong> for this question:</div></v-subheader>
        <v-slider :readonly="!editable" v-model="question.points" thumb-label="always" 
                  min="1" max="5" ticks="always" always-dirty tick-size="5" />
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  name: 'QuestionEditor',
  model: {
    prop: 'question',
    event: 'question-changed'
  },
  props: ['question', 'editable'],
  methods: {
    addVariant() {
      if (!this.question.variants) {
        this.$set(this.question, 'variants', []);
      }
      this.question.variants.push({
        choiceText: '',
        correct: false
      });
    },
    removeVariant(index) {
      this.question.variants.splice(index, 1);
    }
  }
}
</script>

<style>

</style>
