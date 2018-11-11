<template>
  <v-container fluid>
    <v-layout row wrap>
      <v-flex xs12>
        <v-text-field v-model="lesson.name" label="Lesson name"/>
      </v-flex>
      <v-flex xs12>
        <v-radio-group v-model="lesson.type" row>
          <v-subheader class="pl-0"><div>Select <strong>lesson type:</strong></div></v-subheader>
          
          <v-radio label="Lecture" value="lecture"/>
          <v-radio label="Assignment" value="assignment"/>
        </v-radio-group>
      </v-flex>
      <v-flex xs12><v-divider style="margin: -0.5em 0 1em 0;"/></v-flex>
      <v-flex v-if="lesson.type === 'lecture'" xs12>
        <v-textarea v-model="lesson.lectureText" box label="Lecture text" auto-grow/>
      </v-flex>
      <v-flex v-else-if="lesson.type === 'assignment'" xs12>
        <v-expansion-panel>
          <v-expansion-panel-content v-for="(question, i) in lesson.questions" 
                                     :key="`q-${i}}`">
            <v-layout slot="header" justify-start align-center row>
              <div>
                <v-btn small style="margin-top: 0px; margin-bottom: 0px; margin-left: -5px;" 
                       flat icon @click.stop="removeQuestion(i)">
                  <v-icon color="red darken-4">remove</v-icon>
                </v-btn>
              </div>
              <div>Question {{ question.ordering + 1 }}</div>
            </v-layout>
            
            <question-editor v-model="lesson.questions[i]"/>
          </v-expansion-panel-content>
          <v-btn style="margin: 0" color="secondary" block @click="addQuestion">Add question</v-btn>
        </v-expansion-panel>
      </v-flex>
      <v-flex xs12><v-divider style="margin: 1em 0 1em 0;"/></v-flex>
      <v-flex xs12 style="margin: 0 0 0.5em 0;">
        <v-btn color="primary">
          Save lesson
        </v-btn>
        <!-- TODO: IMPLEMENT SAVE LESSON -->
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import QuestionEditor from './QuestionEditor.vue'

export default {
  name: 'LessonEditor',
  components: {QuestionEditor},
  model: {
    prop: 'lesson',
    event: 'lesson-changed'
  },
  props: ['lesson'],
  data() {
    return {
    }
  },
  methods: {
    addQuestion() {
      if (!this.lesson.questions) {
        this.$set(this.lesson, 'questions', []);
      }
      this.lesson.questions.push({
        ordering: this.lesson.questions.length,
        questionText: '',
        type: 'fixed',
        answer: '',
        points: 1,
        variants: []
      });
    },
    removeQuestion(index) {
      this.lesson.questions.splice(index, 1);
      let i = 0;
      this.lesson.questions.forEach(q => q.ordering = (i++));
    }
  }
}
</script>

<style>

</style>
