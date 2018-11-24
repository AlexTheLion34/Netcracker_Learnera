<template>
  <v-container fluid>
    <v-layout column>
      <v-expansion-panel flat>
        <v-expansion-panel-content>
          <div slot="header">Lesson info</div>

          <v-container>
            <v-layout row wrap>
              <v-flex xs12>
                <v-text-field v-model="lesson.name" label="Lesson name" readonly/>
              </v-flex>
              <v-flex xs12>
                <v-radio-group v-model="lesson.type" row readonly>
                  <v-subheader class="pl-0"><div><strong>Lesson</strong> type:</div></v-subheader>
                  
                  <v-radio label="Lecture" value="lecture"/>
                  <v-radio label="Assignment" value="assignment"/>
                </v-radio-group>
              </v-flex>
              <v-flex xs12><v-divider style="margin: -0.5em 0 1em 0;"/></v-flex>
              <v-flex v-if="lesson.type === 'lecture'" xs12>
                <v-textarea v-model="lesson.lectureText" box label="Lecture text" auto-grow readonly/>
              </v-flex>
              <v-flex v-else-if="lesson.type === 'assignment'" xs12>
                <v-expansion-panel>
                  <v-expansion-panel-content v-for="(question, i) in lesson.questions" 
                                             :key="`q-${i}}`">
                    <v-layout slot="header" justify-start align-center row>
                      <div>Question {{ question.ordering + 1 }}</div>
                    </v-layout>
                    
                    <question-editor v-model="lesson.questions[i]" :editable="false"/>
                  </v-expansion-panel-content>
                </v-expansion-panel>
              </v-flex>
            </v-layout> 
          </v-container>    
        </v-expansion-panel-content>
      </v-expansion-panel>
      <v-flex><v-divider style="margin: 1em 0 1em 0;"/></v-flex>
      <v-expansion-panel>
        <v-expansion-panel-content>
          <div slot="header">Student results</div>

          <v-container>
            <v-data-table v-if="lesson.type === 'assignment'"
                          :headers="[
                            {text: 'Name', value: 'name'}, 
                            {text: 'Email', value: 'email'},
                            {text: 'Finished', value: 'finished'},
                            {text: 'Points', value: 'points'} 
                          ]"
                          :items="studentsMapped"
                          hide-actions
                          class="elevation-1"
            >
              <template slot="items" slot-scope="props">
                <td>
                  <v-avatar color="teal" size="36px">
                    <img v-if="props.item.avatar" :src="props.item.avatar">
                    <span v-else class="white--text headline">{{ props.item.name[0] }}</span>
                  </v-avatar>
                  {{ props.item.name }}
                </td>
                <td>
                  {{ props.item.email }}
                </td>
                <td>
                  {{ props.item.finished }}
                </td>
                <td>
                  {{ props.item.points }}
                </td>
              </template>
            </v-data-table>   
          </v-container>
        </v-expansion-panel-content>
      </v-expansion-panel>
      <v-flex><v-divider style="margin: 1em 0 1em 0;"/></v-flex>
      <v-flex>
        <v-layout justify-space-between row>
          <v-btn :disabled="!hasPrev" @click="doPrev()">
            Previous
          </v-btn>
          <v-flex d-flex xs4/>
          <v-btn :disabled="!hasNext" @click="doNext()">
            Next
          </v-btn>
        </v-layout>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import QuestionEditor from './QuestionEditor.vue'

export default {
  name: 'LessonObserver',
  components: {QuestionEditor},
  model: {
    prop: 'lesson',
    event: 'lesson-changed'
  },
  props: ['lesson', 'hasPrev', 'hasNext', 'students'],
  data() {
    return {
    }
  },
  computed: {
    questions: function() {
      return this.lesson.questions;
    },
    studentsMapped: function() {
      if (!this.questions) { 
        return [] 
      }; 

      return this.students.map(student => {
        const name = student.info && (student.info.firstName + ' ' + student.info.lastName) || student.email;
        const email = student.email;
        let finished = false;
        let studentAttempts = [];

        if (this.questions && this.questions[0].attempts) {
          finished = !!this.questions[0].attempts.find(a => a.student === student.id);

          if (finished) {
            studentAttempts = this.lesson.questions.map(q => q.attempts.find(attempt => attempt.student === student.id))
          }
        }

        return {
          name,
          email,
          finished: finished,
          points: studentAttempts.reduce((acc, attempt) => acc + attempt.score, 0)
        };
      });
    }
  },
  watch: {
    questions(val) {
      if (!val) {
        this.questionValids = [];
      } else {
        this.questionValids = val.map(_ => false);
      }
    }
  },
  beforeMount() {

  },
  methods: {
    doPrev() {
      this.$emit('go-prev');
    },
    doNext() {
      this.$emit('go-next');
    },
  }
}
</script>

<style>

</style>
