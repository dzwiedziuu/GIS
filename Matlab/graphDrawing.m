%% Rysowanie wykresów symulowanego wyrza¿ania
% Ten skrypt ma za zadanie narysowaæ wykresy

%% Import biblioteki JAR
% Wymaga wykonania |'mvn assembly:single install'| na obu projektach
%
% * GraphColoringAlgorithm
% * GraphGenerator
%
gcaJarPath = [getenv('USERPROFILE'), '\.m2\repository\GraphColoringAlgorithm\GraphColoringAlgorithm\1.0.0\GraphColoringAlgorithm-1.0.0.jar'];
ggJarPath  = [getenv('USERPROFILE'), '\.m2\repository\GraphGenerator\GraphGenerator\1.0.0\GraphGenerator-1.0.0.jar']; 
ggOutFile  = [getenv('USERPROFILE'), '\Desktop\graph.dat']; 
gcaOutFile = [getenv('USERPROFILE'), '\Desktop\result.dat']; 

ggProbability = 0.3;
ggVertexNum   = 10;

gcaInitTemp = 100;
gcaMinTemp  = 20;
gcaAlfa     = 0.99;

ggCmd = ['java -jar ' ggJarPath ' -p ' num2str(ggProbability) ' -v ' int2str(ggVertexNum) ' -of ' ggOutFile ];

% Wywo³anie generatora grafów
system(ggCmd);

data = zeros(40,3);
T = 60:2:138;
data(:,1) = T';
for i = 1:40
    results = zeros(5,2);
    parfor j = 1:5
        gcaOutFileCur = [gcaOutFile int2str(j)];
        gcaCmd = ['java -jar ' gcaJarPath ' -a ' gcaAlfa ' -i ' ggOutFile ' -o ' gcaOutFileCur ' -ti ' num2str(data(i,1)) ' -tm ' num2str(gcaMinTemp) ];
        system(gcaCmd);
        fileID = fopen(gcaOutFileCur,'r');
        formatSpec = '%f %f';
        out = fscanf(fileID,formatSpec);
        results(j,:) = out;
    end   
    data(i,2:3) = mean(results,1);
end

figure;
plot(data(:,1), data(:,2), data(:,1), data(:,3));
