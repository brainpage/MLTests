jar_files = []
jar_files << Dir['/usr/lib/hadoop/lib/*.jar']
   jar_files << Dir['/usr/lib/hadoop/*.jar']
jar_files << Dir['/usr/lib/mahout/*.jar']

repositories.remote << "http://repo1.maven.org/maven2"
define 'brainpage' do
	project.version = '1.0'
	compile.with jar_files
	package :jar, :id=>"test_recommender"
end
