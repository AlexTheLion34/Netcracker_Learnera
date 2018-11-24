<template>
  <course-list :items="courses"
               :actions="actions"
               @action-clicked="onActionClicked"/>
</template>

<script>
import {mapState, mapActions, mapGetters} from 'vuex'
import ItemList from './ItemList.vue'
import CourseList from './CourseList.vue'

export default {
  name: 'UserCourseList',
  components: {ItemList, CourseList},
  props: ['user', 'actions', 'filter'],
  data() {
    return {
    };
  },
  computed: {
    ...mapGetters('courses', {coursesByTeacherId: 'coursesByTeacherId'}),
    courses: function() {
      if (!this.user || !this.user.role) {
        return [];
      }
      let ret = [];
      if (this.user.role === 'TEACHER') {
        ret = this.coursesByTeacherId(this.user.id);
      } else if (this.user.role === 'STUDENT') {
        ret = [...new Set(this.user.studyGroups.map(g => {
          return g.courses.map(c => {
            return {...c, groups:[]}
          });
        }).flat())];
      }
      if (this.filter) {
        ret = ret.filter(this.filter);
      }
      return ret;
    }
  },
  watch: {
    user: function(val) {
      if (val && val.id && val.role === 'TEACHER') {
        this.getCoursesByTeacherId(this.user.id);
      }
    }
  },
  created() {
    if (this.user.id && this.user.role === 'TEACHER') {
      this.getCoursesByTeacherId(this.user.id);
    }
  },
  methods: {
    ...mapActions('courses', {
      getCoursesByTeacherId: 'getByTeacherId'
    }),
    onActionClicked({name, index}) {
      this.$emit('action-clicked', {name, course: this.courses[index]} );
    }
  }
}
</script>
